package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-viaje", url = "http://localhost:8084/viajes")
public interface ViajeFeingClient {
}
