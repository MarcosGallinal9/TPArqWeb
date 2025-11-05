package org.example.viaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ViajeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajeApplication.class, args);
    }
        SpringApplication.run(org.example.viaje.class, args);
}
