package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8085/cuenta")
public interface CuentaFeingClient {
}
