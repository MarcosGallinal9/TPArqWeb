package org.example.viaje.feignClients;

import org.example.viaje.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8087/tarifas")
public interface TarifaFeignClient {

    /**
     * Obtiene la tarifa vigente.
     * GET /tarifas/vigente
     * @return TarifaDTO
     */
    @GetMapping("/vigente")
    TarifaDTO getTarifaVigente();

    @GetMapping("/{id}")
    public TarifaDTO getTarifaById(@PathVariable("id") String id);
}
