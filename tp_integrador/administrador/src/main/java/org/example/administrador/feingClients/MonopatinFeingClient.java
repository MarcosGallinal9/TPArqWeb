package org.example.administrador.feingClients;

import org.example.administrador.dto.MonopatinDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@FeignClient(name = "microservicio-monopatin", url = "http://localhost:8082/monopatines")
public interface MonopatinFeingClient {

    @GetMapping("/")
    ResponseEntity<List<MonopatinDTO>> getAllMonopatines();
    @PutMapping("/{id}/evaluar-mantenimiento")
    ResponseEntity<String> evaluarMantenimiento(@PathVariable("id") String id);

}
