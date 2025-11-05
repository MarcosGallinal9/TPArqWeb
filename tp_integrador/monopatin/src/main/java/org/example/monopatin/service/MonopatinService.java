package org.example.monopatin.service;

import org.example.monopatin.dto.ViajeDTO;
import org.example.monopatin.feignClient.ViajeFeignClient;
import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.repository.MonopatinRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MonopatinService {

    MonopatinRepository monopatinRepository;
    ViajeFeignClient viajeFeignClient;

    public MonopatinService(MonopatinRepository monopatinRepository) {
        this.monopatinRepository = monopatinRepository;
    }

    public List<Monopatin> getAll(){
        return monopatinRepository.findAll();
    }
    public Monopatin save(Monopatin monopatin){
        return monopatinRepository.save(monopatin);
    }
    public void delete(Monopatin monopatin){
        monopatinRepository.delete(monopatin);
    }

    public Monopatin findById(String id){
        return monopatinRepository.findById(id).orElse(null);
    }

    public Monopatin update(Monopatin monopatin){
        return monopatinRepository.save(monopatin);
    }

    /**
     * Calcula y actualiza el tiempo total de uso y los km totales
     * para verificar si el monopatín requiere de mantenimiento.
     * @param idMonopatin ID del monopatín a evaluar.
     */
    public void evaluarMantenimiento(String idMonopatin) {
        Monopatin monopatin = monopatinRepository.findById(idMonopatin)
                .orElseThrow(() -> new RuntimeException("Monopatín no encontrado."));

        // 1. Consultar todos los viajes del monopatín
        List<ViajeDTO> viajes = viajeFeignClient.getViajesByMonopatinId(idMonopatin);

        float kmTotales = 0;
        long tiempoTotalUsoSegundos = 0;

        for (ViajeDTO viaje : viajes) {
            // Solo considerar viajes finalizados
            if (viaje.getFin() != null) {
                kmTotales += viaje.getKmRecorridos();

                long diffInMillis = viaje.getFin().getTime() - viaje.getInicio().getTime();
                // NOTA: Se necesita la lógica de PAUSAS del MS Viaje para descontar el tiempo de pausa
                // Asumiremos que el ViajeDTO NO incluye el tiempo de pausa y que los kilómetros son los reportados.
                tiempoTotalUsoSegundos += TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
            }
        }

        // 2. Actualizar las métricas en la entidad
        monopatin.setKmRecorridos(kmTotales);
        // Si el MS Viaje te da el tiempo de uso NETO (sin pausas), úsalo. Si no, necesitarás un PausaFeignClient.
        monopatin.setTiempoUso(tiempoTotalUsoSegundos);

        // 3. Evaluar la necesidad de mantenimiento
        // Mantenimiento si excede 500 km O 500000 segundos (ejemplo de regla)
        if (monopatin.getKmRecorridos() > 500 || monopatin.getTiempoUso() > 500000) {
            monopatin.setEstado("mantenimiento");
            System.out.println("Monopatín " + idMonopatin + " marcado para mantenimiento.");
        }

        monopatinRepository.save(monopatin);
    }
}
