package org.example.monopatin.feignClient;

import org.example.monopatin.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-viaje", url = "http://localhost:8085/viajes")
public interface ViajeFeignClient {
    /**
     * Obtiene todos los viajes asociados a un ID de Monopatín.
     * GET /viajes/byMonopatin/{id}
     */
    @GetMapping("/byMonopatin/{monopatinId}")
    List<ViajeDTO> getViajesByMonopatinId(@PathVariable("monopatinId") String monopatinId);
}
