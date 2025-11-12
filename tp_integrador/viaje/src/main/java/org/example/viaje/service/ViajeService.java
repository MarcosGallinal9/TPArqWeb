package org.example.viaje.service;
import feign.FeignException;
import org.example.viaje.dto.*;
import org.example.viaje.entity.Pausa;
import org.example.viaje.entity.Viaje;
import org.example.viaje.feignClients.*;
import org.example.viaje.repository.ViajeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ViajeService {

    ViajeRepository viajeRepository;

    // Clientes de otros microservicios
    MonopatinFeignClient monopatinFeignClient;
    ParadaFeingClient paradaFeignClient;
    TarifaFeignClient tarifaFeignClient;
    FacturacionFeignClient facturacionFeignClient;
    CuentaFeignClient cuentaFeignClient;
    UsuarioFeignClient usuarioFeignClient;

    // Servicios locales (para Pausa)
      PausaService pausaService;

    public ViajeService(ViajeRepository viajeRepository, MonopatinFeignClient monopatinFeignClient, ParadaFeingClient paradaFeignClient, TarifaFeignClient tarifaFeignClient, FacturacionFeignClient facturacionFeignClient, CuentaFeignClient cuentaFeignClient, UsuarioFeignClient usuarioFeignClient, PausaService pausaService) {
        this.viajeRepository = viajeRepository;
        this.monopatinFeignClient = monopatinFeignClient;
        this.paradaFeignClient = paradaFeignClient;
        this.tarifaFeignClient = tarifaFeignClient;
        this.facturacionFeignClient = facturacionFeignClient;
        this.cuentaFeignClient = cuentaFeignClient;
        this.usuarioFeignClient = usuarioFeignClient;
        this.pausaService = pausaService;
    }

    public List<Viaje> getAll(){
        return viajeRepository.findAll();
    }

    public Viaje save(Viaje viaje) {

        //Obtener la información actual del monopatín
        MonopatinDTO monopatinActual = monopatinFeignClient.getMonopatin(viaje.getIdMonopatin());
        //Crear el DTO con el nuevo estado
        MonopatinDTO monopatinUpdate = new MonopatinDTO();
        monopatinUpdate.setId(viaje.getIdMonopatin());
        monopatinUpdate.setEstado("en_uso");
        //Llamar al microservicio Monopatín para actualizar su estado
        MonopatinDTO monopatinConfirmado = monopatinFeignClient.updateMonopatin(monopatinUpdate.getId(), monopatinUpdate);

        if (monopatinConfirmado != null && "en_uso".equals(monopatinConfirmado.getEstado())) {
            //Si la actualización fue exitosa, guardar el viaje.
            return viajeRepository.save(viaje);
        } else {
            // Manejar la excepción (ej: Monopatín ya en uso o error de servicio)
            throw new RuntimeException("No se pudo iniciar el viaje: Monopatín no disponible.");
        }
    }
    /**
     * Inicia un nuevo viaje.
     * @param viaje El objeto Viaje con idMonopatin, idUsuario, idParadaInicio, idTarifa.
     * @return El Viaje guardado.
     */
    public Viaje iniciarViaje(Viaje viaje) {

        //Validar Monopatín y Parada de inicio
        MonopatinDTO monopatin = monopatinFeignClient.getMonopatin(viaje.getIdMonopatin());
        ParadaDTO paradaInicio = paradaFeignClient.getParada(viaje.getIdParadaInicio());

        if (monopatin == null || paradaInicio == null || !"disponible".equalsIgnoreCase(monopatin.getEstado())) {
            throw new RuntimeException("No se puede iniciar el viaje. Monopatín no disponible o parada inválida.");
        }

        //Marcar Monopatín como "en_uso"
        monopatin.setEstado("en_uso");
        monopatinFeignClient.updateMonopatin(monopatin.getId(), monopatin);

        //Registrar Viaje
        viaje.setInicio(new Date());
        viaje.setKmRecorridos(0); // Inicia en cero
        viaje.setFin(null); // Aún no finaliza

        return viajeRepository.save(viaje);
    }

    /**
     * Finaliza un viaje.
     * @param idViaje ID del viaje a finalizar.
     * @param idParadaFin ID de la parada donde se deja el monopatín.
     * @param kmRecorridosFinal Kilómetros reportados por el monopatín.
     * @return El Viaje finalizado.
     */
    public Viaje finalizarViaje(String idViaje, String idParadaFin, float kmRecorridosFinal) {
        Viaje viaje = viajeRepository.findById(idViaje).orElseThrow(() -> new RuntimeException("Viaje no encontrado."));

        // Validar Parada de destino
        ParadaDTO paradaFin = paradaFeignClient.getParada(idParadaFin);

        // Verificar que el monopatin se encuentra en una parada permitida
        if (paradaFin == null) {
            throw new RuntimeException("No se puede finalizar el viaje. El monopatín no está en una parada permitida.");
        }

        // Finalizar el registro del Viaje
        viaje.setFin(new Date());
        viaje.setKmRecorridos(kmRecorridosFinal);
        viaje.setIdParadaFin(idParadaFin);
        Viaje viajeFinalizado = viajeRepository.save(viaje);

        //Marcar Monopatín como "disponible"
        MonopatinDTO monopatin = new MonopatinDTO();
        monopatin.setId(viaje.getIdMonopatin());
        monopatin.setEstado("Disponible");
        monopatinFeignClient.updateMonopatin(monopatin.getId(), monopatin);

        calcularYCobrarViaje(viajeFinalizado);

        return viajeFinalizado;
    }
    public List<Viaje> byMonopatinId(String idMonopatin){
        return viajeRepository.findByIdMonopatin(idMonopatin);
    }

    /**
     * Calcula el costo final del viaje y notifica al MS Facturación.
     * @param viajeFinalizado La entidad Viaje con inicio/fin y IDs.
     */
    public void calcularYCobrarViaje(Viaje viajeFinalizado) {

        // Calcular la duracion del viaje
        long tiempoEnSegundos = viajeFinalizado.getFin().getTime() - viajeFinalizado.getInicio().getTime();
        long tiempoTotalMinutos = TimeUnit.MILLISECONDS.toMinutes(tiempoEnSegundos); // Incluye pausas

        // Obtener pausas (PausaService)
        List<Pausa> pausas = pausaService.findByViajeId(viajeFinalizado.getId());

        boolean aplicaRecargoExtraPausa = pausas.stream()
                .anyMatch(p -> p.getFin() != null &&
                        TimeUnit.MILLISECONDS.toMinutes(p.getFin().getTime() - p.getInicio().getTime()) > 15);

        //Obtener tarifa y validar la cuenta
        TarifaDTO tarifa = tarifaFeignClient.getTarifaById(viajeFinalizado.getIdTarifa());
        CuentaDTO cuenta = cuentaFeignClient.getCuenta(viajeFinalizado.getIdCuenta());

        if (tarifa == null) {
            throw new RuntimeException("No se puede facturar: Tarifa no encontrada.");
        }
        if (cuenta == null || !cuenta.getEstado()) {
            throw new RuntimeException("No se puede facturar: Cuenta inválida o anulada.");
        }

          // Tarifa base (común o premium)
        double costoPorMinutoBase = "PREMIUM".equalsIgnoreCase(cuenta.getTipoCuenta()) ?
                tarifa.getValorPremium() : tarifa.getValorComun();

        // Declarar e inicializar aquí para que compile
        double costoTotal = costoPorMinutoBase * tiempoTotalMinutos; // Costo base (tiempo total usado)
        String detalle = "BÁSICA";
        if ("PREMIUM".equalsIgnoreCase(cuenta.getTipoCuenta())) {
            detalle = "PREMIUM"; //Si la cuenta es premium agregar en factura la logica
        }

        // Aplica cargo por pausa extendida
        if (aplicaRecargoExtraPausa) {
            // Se aplica la tarifa extra por el tiempo total de servicio
            double costoRecargoExtra = tarifa.getValorExtrapausa() * tiempoTotalMinutos;
            costoTotal += costoRecargoExtra;

        }
        //Notificar facturacion
        FacturacionDTO factura = new FacturacionDTO(
                new Date(),
                viajeFinalizado.getId(),
                viajeFinalizado.getIdUsuario(),
                costoTotal
        );
        facturacionFeignClient.registrarCobro(factura);
    }

    /**
     * Realiza la agregación de viajes, contando cuántos viajes tuvo cada monopatín en un año dado.
     * @param year Año a filtrar.
     * @return Lista de ReporteMonopatinContadorViajes.
     */
    public List<ReporteMonopatinContadorViajes> getConteoViajesPorMonopatinYAnio(int year) {
        return viajeRepository.contadorViajesXAnio(year);
    }

    public List<UsuarioUsoDTO> obtenerUsuariosMasActivos(Date inicio, Date fin, List<String> userIds) {

        // 1. Obtener los viajes filtrados por FECHA.
        List<Viaje> viajes = viajeRepository.findByFechaBetween(inicio, fin);

        // 2. FILTRAR por los IDs de usuario proporcionados (los que cumplen con el rol)
        List<Viaje> viajesFiltradosPorUsuario = viajes.stream()
                .filter(viaje -> userIds.contains(viaje.getIdUsuario()))
                .toList();

        // 3. Sumar los km recorridos por usuario
        Map<String, Double> kmPorUsuario = viajesFiltradosPorUsuario.stream()
                .collect(Collectors.groupingBy(
                        Viaje::getIdUsuario,
                        Collectors.summingDouble(Viaje::getKmRecorridos)
                ));

        // 4. Obtener los detalles del usuario y construir el DTO final
        List<UsuarioUsoDTO> lista = kmPorUsuario.entrySet().stream()
                .map(entry -> {
                    UsuarioUsoDTO usuario = null;
                    try {
                        // Llama al microservicio Usuario
                        usuario = usuarioFeignClient.getUsuarioById(entry.getKey());
                    } catch (FeignException e) {
                        // Capturamos el error (ej: 404 Not Found si el usuario fue borrado)
                        System.err.println("Advertencia: Usuario " + entry.getKey() + " no encontrado en MS Usuario. Skipping.");
                        return null; // Saltamos este usuario.
                    } catch (Exception e) {
                        // Capturar otros errores de conexión o genéricos
                        System.err.println("Error de conexión con MS Usuario para ID " + entry.getKey() + ": " + e.getMessage());
                        return null;
                    }

                    if (usuario != null) {
                        // Ya no necesitas el filtro de rol aquí, ¡el MS Admin lo hizo!
                        return new UsuarioUsoDTO(
                                usuario.getId(),
                                usuario.getNombre(),
                                usuario.getRol(),
                                entry.getValue() // Kilómetros
                        );
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(UsuarioUsoDTO::getKmRecorridos).reversed())
                .collect(Collectors.toList());

        return lista;
    }


        //punto h
    public ReporteUsoDTO getReporteUsoDto(List<String> userIds, LocalDate fechaInicio, LocalDate fechaFin) {
        return viajeRepository.tiempoUsoUsuario(userIds, fechaInicio, fechaFin);
    }

    public void delete(Viaje viaje){
        viajeRepository.delete(viaje);
    }

    public Viaje findById(String id){
        return viajeRepository.findById(id).orElse(null);
    }

    public Viaje update(Viaje viaje){
        return viajeRepository.save(viaje);
    }

    public List<Viaje> byUserId(String userid){
        return viajeRepository.findByIdUsuario(userid);
    }
}
