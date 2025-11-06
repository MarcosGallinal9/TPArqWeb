package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-parada", url = "http://localhost:8083/paradas")
public interface ParadaFeingClient {
}
