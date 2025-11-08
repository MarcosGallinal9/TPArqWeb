package org.example.administrador.feingClients;

import org.example.administrador.dto.FacturasXRangoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-facturacion", url = "http://localhost:8086/facturacion")
public interface FacturacionFeingClient {
    @GetMapping("/facturadoenrango")
    ResponseEntity<Double> getTotalFacturado(
            @RequestParam("anio") int anio,
            @RequestParam("mesInicio") int mesInicio,
            @RequestParam("mesFin") int mesFin
    );
}
