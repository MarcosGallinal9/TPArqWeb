package org.example.viaje.service;

import org.example.viaje.dto.MonopatinDTO;
import org.example.viaje.dto.ParadaDTO;
import org.example.viaje.entity.Viaje;
import org.example.viaje.feignClients.MonopatinFeignClient;
import org.example.viaje.feignClients.TarifaFeignClient;
import org.example.viaje.repository.ViajeRepository;
import org.example.viaje.feignClients.ParadaFeingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ViajeService {

    ViajeRepository viajeRepository;

    // Clientes de otros microservicios
//    @Autowired
    MonopatinFeignClient monopatinFeignClient;
    @Autowired
    ParadaFeingClient paradaFeignClient;
    @Autowired
    TarifaFeignClient tarifaFeignClient;

    // Servicios locales (para Pausa)
    @Autowired
    PausaService pausaService;

    public ViajeService(ViajeRepository viajeRepository) {
        this.viajeRepository = viajeRepository;
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
        MonopatinDTO monopatinConfirmado = monopatinFeignClient.updateMonopatin(monopatinUpdate);

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

        // 1. Validar Monopatín y Parada de inicio
        MonopatinDTO monopatin = monopatinFeignClient.getMonopatin(viaje.getIdMonopatin());
        ParadaDTO paradaInicio = paradaFeignClient.getParada(viaje.getIdParadaInicio());

        if (monopatin == null || paradaInicio == null || !"disponible".equals(monopatin.getEstado())) {
            throw new RuntimeException("No se puede iniciar el viaje. Monopatín no disponible o parada inválida.");
        }

        // 2. Marcar Monopatín como "en_uso"
        monopatin.setEstado("en_uso");
        monopatinFeignClient.updateMonopatin(monopatin);

        // 3. Registrar Viaje
        viaje.setInicio(new Date());
        viaje.setKmRecorridos(0); // Inicia en cero
        viaje.setFin(null); // Aún no finaliza
        // Nota: Asume que idCuenta se obtuvo del MS Usuario/Cuenta

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

        // 1. Validar Parada de destino
        ParadaDTO paradaFin = paradaFeignClient.getParada(idParadaFin);

        // NOTA DE ENUNCIADO: "la app no debe permitir finalizar un viaje si no detecta, mediante el GPS con el que cuenta el monopatín,
        // que se encuentra en una parada permitida" [cite: 24]
        if (paradaFin == null) {
            throw new RuntimeException("No se puede finalizar el viaje. El monopatín no está en una parada permitida.");
        }

        // 2. Finalizar el registro del Viaje
        viaje.setFin(new Date());
        viaje.setKmRecorridos(kmRecorridosFinal);
        viaje.setIdParadaFin(idParadaFin);
        Viaje viajeFinalizado = viajeRepository.save(viaje);

        // 3. Marcar Monopatín como "disponible" (o "mantenimiento" si aplica, aunque eso es lógica del MS Monopatín)
        MonopatinDTO monopatin = new MonopatinDTO();
        monopatin.setId(viaje.getIdMonopatin());
        monopatin.setEstado(viaje.getIdMonopatin());
        // Aquí se pueden sumar los km/tiempo en el MS Monopatín. Para simplificar, solo actualizamos el estado.
        monopatinFeignClient.updateMonopatin(monopatin);

        //FALTA LOGICA PARA CALCULAR LA TARIFA

        return viajeFinalizado;
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

    public List<Viaje> byUserId(Long userid){
        return viajeRepository.findByIdUsuario(userid);
    }
}
