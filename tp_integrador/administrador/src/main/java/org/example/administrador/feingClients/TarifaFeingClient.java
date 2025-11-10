package org.example.administrador.feingClients;

import org.example.administrador.dto.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "microservicio-tarifa", url = "http://localhost:8087/tarifa")
public interface TarifaFeingClient {

    @PutMapping("/ajuste")
    void actualizarTarifas(@RequestBody TarifaDTO tarifaDTO,@RequestParam("fechaActivacion") LocalDate fechaActivacion);
}
