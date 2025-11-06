package org.example.administrador.feingClients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8081/usuario")
public interface UsuarioFeingClient {
}
