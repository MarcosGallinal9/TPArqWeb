package org.example.administrador.feingClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8085/cuenta")
public interface CuentaFeingClient {
    /**
     * Anula una cuenta (cambia el estado a FALSE) consultando a Cuenta.
     */
    @PostMapping("/anular/{id}")
    ResponseEntity<Void> anularCuenta(@PathVariable("id") String id);
}
