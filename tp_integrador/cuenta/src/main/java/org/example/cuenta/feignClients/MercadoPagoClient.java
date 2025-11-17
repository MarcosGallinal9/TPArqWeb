package org.example.cuenta.feignClients;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface MercadoPagoClient {
    public boolean validarPago(@RequestParam ("monto") double monto, @RequestParam ("tokenPago" ) String tokenPago);
}
