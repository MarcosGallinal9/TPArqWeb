package org.example.cuenta.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient
public interface MercadoPagoClient {
    boolean validarPago(@RequestParam ("monto") double monto, @RequestParam ("tokenPago" ) String tokenPago);
}
