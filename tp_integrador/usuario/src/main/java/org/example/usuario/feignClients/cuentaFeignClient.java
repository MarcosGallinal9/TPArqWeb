package org.example.usuario.feignClients;
import org.example.usuario.dto.cuentaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-cuenta", url = "http://localhost:8085/cuenta")
public interface cuentaFeignClient {

    /**
     * Obtiene los detalles de la cuenta por su ID
     * Corresponde al GET /cuenta/{id} en  Cuenta.
     * @param id El ID de la cuenta (Long).
     * @return El DTO de la Cuenta.
     */
    @GetMapping("/{id}")
    public cuentaDto getCuenta(@PathVariable("id") Long id);


}