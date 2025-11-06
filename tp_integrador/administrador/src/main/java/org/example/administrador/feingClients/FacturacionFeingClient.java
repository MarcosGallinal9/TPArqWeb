package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-facturacion", url = "http://localhost:8086/facturacion")
public interface FacturacionFeingClient {
}
