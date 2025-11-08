package org.example.usuario.feignClients;
import org.example.usuario.dto.cuentaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8085/cuenta")

public interface cuentaFeignClient {

    @GetMapping("/{id}")
    public cuentaDto getCuenta(@PathVariable("id") String id);

}