package org.example.monopatin.service;

import org.example.monopatin.dto.ViajeDTO;
import org.example.monopatin.feignClient.ViajeFeignClient;
import org.example.monopatin.entity.Monopatin;
import org.example.monopatin.repository.MonopatinRepository;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.stereotype.Service;

import java.awt.*;
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

        // Consultar todos los viajes del monopatín
        List<ViajeDTO> viajes = viajeFeignClient.getViajesByMonopatinId(idMonopatin);

        float kmTotales = 0;
        long tiempoTotalUsoNetoSegundos = 0; // Se inicializa para el tiempo REAL de uso

        for (ViajeDTO viaje : viajes) {
            // Solo considerar viajes finalizados
            if (viaje.getFin() != null) {
                kmTotales += viaje.getKmRecorridos();

                //Calcular duración BRUTA (Inicio a Fin)
                long duracionTotalSegundos = TimeUnit.MILLISECONDS.toSeconds(
                        viaje.getFin().getTime() - viaje.getInicio().getTime()
                );

                //Consultar el tiempo total de pausa desde Viaje
                Long tiempoPausaSegundos = 0L;
                try {
                    tiempoPausaSegundos = viajeFeignClient.getTiempoTotalPausaSegundos(viaje.getId());
                } catch (Exception e) {
                    System.err.println("Advertencia: No se pudo obtener tiempo de pausa para viaje " + viaje.getId());
                }

                //Calcular el tiempo de uso NETO
                long tiempoUsoNeto = duracionTotalSegundos - tiempoPausaSegundos;
                if (tiempoUsoNeto < 0) {
                    tiempoUsoNeto = 0; // Evitar tiempos negativos si hay errores de registro
                }

                tiempoTotalUsoNetoSegundos += tiempoUsoNeto;
            }
        }

        // Actualizar las métricas en la entidad
        monopatin.setKmRecorridos(kmTotales);
        monopatin.setTiempoUso(tiempoTotalUsoNetoSegundos);

        // Mantenimiento si excede X km O Y segundos de uso neto.
        if (monopatin.getKmRecorridos() > 500 || monopatin.getTiempoUso() > 500000) {
            monopatin.setEstado("mantenimiento");
            System.out.println("Monopatín " + idMonopatin + " marcado para mantenimiento.");
        }

        monopatinRepository.save(monopatin);
    }


    public List<Monopatin> buscarCercanos(double lat, double lng, double radiokm){
        Point ubicacionUsuario = new Point( lng,lat);
        Distance radio = new Distance(radiokm, Metrics.KILOMETERS);

        return monopatinRepository.findByEstadoAndUbicacionNear("DISPONIBLE", ubicacionUsuario, radio);
    }
}
