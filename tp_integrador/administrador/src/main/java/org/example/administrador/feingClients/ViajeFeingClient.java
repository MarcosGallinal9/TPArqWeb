package org.example.administrador.feingClients;

import org.example.administrador.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
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
}
