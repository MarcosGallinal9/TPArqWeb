package org.example.integrador_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "service", "repository"})
@EnableJpaRepositories(basePackages = {"repository"})
@EntityScan(basePackages = {"entity"})
public class Integrador3Application {

    public static void main(String[] args) {
        SpringApplication.run(Integrador3Application.class, args);
    }

}
