package org.example.usuario.feignClients;
import org.example.usuario.config.FeignClientConfig;
import org.example.usuario.dto.cuentaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8085/cuenta", configuration = FeignClientConfig.class)

public interface cuentaFeignClient {

    @GetMapping("/{id}")
    cuentaDto getCuenta(@PathVariable("id") String id);

    @GetMapping("/{id}/usuarios")
    List<String> getUsuariosAsociados(String nroCuenta);
}