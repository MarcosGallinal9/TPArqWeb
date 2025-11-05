package org.example.viaje.feignClients;

import org.example.viaje.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8083/tarifas")
public interface TarifaFeignClient {

    /**
     * Obtiene la tarifa vigente.
     * GET /tarifas/vigente
     * @return TarifaDTO
     */
    TarifaDTO getTarifaVigente();
}
