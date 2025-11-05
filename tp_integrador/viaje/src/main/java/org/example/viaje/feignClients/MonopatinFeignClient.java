package org.example.viaje.feignClients;

import org.example.viaje.dto.MonopatinDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8081/monopatines")
public interface MonopatinFeignClient {
    /**
     * Obtiene un Monopatin del servicio Monopatin
     * GET /monopatines/{id}
     */
    @GetMapping("{id}")
    MonopatinDTO getMonopatin(@PathVariables("id") String id);

    /**
     *   Actualiza el estado de un Monopatin
     *   POST /PUT en el servicio monopatin para actualizar la entidad
     */
    @PutMapping("")
    MonopatinDTO updateMonopatin(@RequestBody MonopatinDTO monopatin);
}
