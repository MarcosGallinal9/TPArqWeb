package org.example.administrador.service;

import feign.FeignException;
import org.example.administrador.dto.*;
import org.example.administrador.entity.Admin;
import org.example.administrador.feingClients.*;
import org.example.administrador.repository.AdminRepository;
import org.example.administrador.dto.ReporteUsoDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    //Feing clients
    MonopatinFeingClient monopatinFeingClient;
    ViajeFeingClient viajeFeingClient;
    CuentaFeingClient cuentaFeingClient;
    FacturacionFeingClient facturacionFeingClient;
    UsuarioFeingClient usuarioFeingClient;
    TarifaFeingClient tarifaFeingClient;
    
    AdminRepository adminRepository;

    public AdminService(MonopatinFeingClient monopatinFeingClient, ViajeFeingClient viajeFeingClient, CuentaFeingClient cuentaFeingClient, AdminRepository adminRepository, FacturacionFeingClient facturacionFeingClient, UsuarioFeingClient usuarioFeingClient) {
        this.monopatinFeingClient = monopatinFeingClient;
        this.viajeFeingClient = viajeFeingClient;
        this.cuentaFeingClient = cuentaFeingClient;
        this.adminRepository = adminRepository;
        this.facturacionFeingClient = facturacionFeingClient;
        this.usuarioFeingClient = usuarioFeingClient;
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    /**
     * PUNTO A
     * Genera el reporte consolidado de uso (Km, Tiempo Neto y Tiempo Total) para todos los monopatines.
     * Cumple con el requisito: "Este reporte debe poder configurarse para incluir (o no) los tiempos de pausa."
     *
     * @return Lista de ReporteMonopatinXKm con las métricas.
     */
    public List<ReporteMonopatinXKm> generarReporteUso(boolean pausas) {

        ResponseEntity<List<MonopatinDTO>> response = monopatinFeingClient.getAllMonopatines();
        try {
            // La llamada que puede fallar
            response = monopatinFeingClient.getAllMonopatines();
        } catch (FeignException e) {
            // Loguear la excepción de Feign (muestra el error de conexión, 404, 500 del otro lado, etc.)
            System.err.println("Feign Error al obtener monopatines: " + e.status() + " | Mensaje: " + e.getMessage());
            // Lanza una excepción personalizada o un RuntimeException descriptivo
            throw new RuntimeException("Error de comunicación con MS-Monopatín (Código: " + e.status() + "). Detalles: " + e.getMessage(), e);
        } catch (Exception e) {
            // Captura fallas de conexión (ej. MS no corriendo)
            System.err.println("Error de conexión al obtener monopatines: " + e.getMessage());
            throw new RuntimeException("Fallo al conectar con MS-Monopatín.", e);
        }

        List<MonopatinDTO> monopatines = response.getBody();
        if (monopatines == null || !response.getStatusCode().is2xxSuccessful()) {
            return new ArrayList<>(); // Devuelve lista vacía
        }
        List<ReporteMonopatinXKm> reportes = new ArrayList<>();

        for (MonopatinDTO monopatin : monopatines) {

            // Obtener métricas NETAS desde la entidad Monopatín
            Long tiempoUsoNetoSegundos = monopatin.getTiempoUso();
            float kmRecorridosFloat = monopatin.getKmRecorridos();
            Long kmRecorridos = (long) kmRecorridosFloat;

            //Obtener el listado de VIAJES del Monopatín (MS Viaje)
            List<ViajeDTO> viajesDelMonopatin = viajeFeingClient.getViajesByMonopatinId(monopatin.getId());

            long tiempoTotalPausaSegundos = 0;

            //Iterar sobre los viajes para sumar el tiempo de pausa
            for (ViajeDTO viaje : viajesDelMonopatin) {
                // Solo consulta pausas para viajes finalizados
                if (viaje.getFin() != null) {
                    try {
                        // Obtener la suma de pausas de ESTE viaje
                        Long pausaSegundosViaje = viajeFeingClient.getTiempoTotalPausaSegundos(viaje.getId());
                        tiempoTotalPausaSegundos += pausaSegundosViaje;
                    } catch (Exception e) {
                        System.err.println("Error al obtener pausa del viaje " + viaje.getId() + ": " + e.getMessage());
                    }
                }
            }

            // Calcular el tiempo TOTAL (incluyendo pausas)
            Long tiempoDeUsoTotalSegundos = tiempoUsoNetoSegundos + tiempoTotalPausaSegundos;
            if(pausas){
                ReporteMonopatinConPausas entrada = new ReporteMonopatinConPausas(
                monopatin.getId(),
                kmRecorridos,
                tiempoDeUsoTotalSegundos);
                reportes.add(entrada);
            }else{
                ReporteMonopatinSinPausas entrada = new ReporteMonopatinSinPausas(
                        monopatin.getId(), kmRecorridos, tiempoUsoNetoSegundos);
                reportes.add(entrada);
            }


        }

        return reportes;
    }

    /**
     * PUNTO B
     * Anula una cuenta de usuario, inhabilitando su uso.
     * @param idCuenta ID de la cuenta a anular.
     * @return El DTO de la Cuenta actualizada.
     */
    public void anularCuentaUsuario(String idCuenta) {

        ResponseEntity<Void> response = cuentaFeingClient.anularCuenta(idCuenta);

        // Si Cuenta devuelve 204 (No Content) o 200, es exitoso.
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al anular la cuenta " + idCuenta + " en el MS Cuenta. Código de estado: " + response.getStatusCodeValue());
        }


    }

    /**
     * PUNTO C
     * Consulta los monopatines con más de X viajes en un cierto año
     * @param minViajes Mínimo de viajes (X).
     * @param year Año de filtrado.
     * @return Lista de Monopatines que superan la cantidad de viajes.
     */
    public List<ReporteMonopatinContadorViajes> getMonopatinesConMasDeXViajes(int minViajes, int year) {

        // Obtener de Viaje el conteo agregado de todos los monopatines en ese año
        List<ReporteMonopatinContadorViajes> todosLosMonopatines = viajeFeingClient.getMonopatinesPorViajes(year);

        // Filtra la lista localmente para obtener solo aquellos con más de X viajes
        return todosLosMonopatines.stream()
                .filter(m -> m.getCantidadViajes() >= minViajes)
                .toList();
    }

    /**
     * PUNTO D
     */
    public Double obtenerTotalFacturado(int anio, int mesInicio, int mesFin) {
        return facturacionFeingClient.getTotalFacturado(anio, mesInicio, mesFin).getBody();
    }

    /**
     * Punto E
     */

    public ReporteUsoDTO getUsuariosQueMasUsanMonopatines(String rol, LocalDate inicio, LocalDate fin) {
        // Obtener todos los usuarios con ese rol
        List<UsuarioUsoDTO> usuarios = usuarioFeingClient.getUsuarios();

        List<String> userIds = usuarios.stream()
                .filter(u -> u.getRol().equalsIgnoreCase(rol))
                .map(UsuarioUsoDTO::getId)
                .toList();

        // Llamar al MS de viajes para obtener el uso de esos usuarios
        return viajeFeingClient.getReporteUso(userIds, inicio, fin);
    }

    /**
     * PUNTO F
     * @param tarifaDTO
     * @param fechaActivacion
     */
    public void ajustarTarifas(TarifaDTO tarifaDTO,LocalDate fechaActivacion) {
        tarifaDTO.setFechaActivacion(fechaActivacion);
        tarifaFeingClient.actualizarTarifas(tarifaDTO);
    }
}
