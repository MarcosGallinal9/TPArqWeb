package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8087/tarifa")
public interface TarifaFeingClient {
}
