package org.example.administrador.feingClients;

import org.example.administrador.dto.ReporteMonopatinContadorViajes;
import org.example.administrador.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-viaje", url = "http://localhost:8084/viajes")
public interface ViajeFeingClient {
    /**
     * Consulta a Viaje (PausaController) para obtener el tiempo total de pausa.
     */
    @GetMapping("/pausas/tiempo-total")
    Long getTiempoTotalPausaSegundos(@RequestParam("idViaje") String idViaje);

    List<ViajeDTO> getViajesByMonopatinId(String monopatin);

    /**
     * Consulta a Viaje para obtener el conteo de viajes por monopatín, filtrado por año.
     * GET /viajes/reportes/monopatines-por-viajes?year={year}
     */
    @GetMapping("/reportes/monopatines-por-viajes")
    List<ReporteMonopatinContadorViajes> getMonopatinesPorViajes(@RequestParam("year") int year);
}