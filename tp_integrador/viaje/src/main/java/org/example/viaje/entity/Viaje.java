package org.example.viaje.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date inicio;
    private Date fin;
    private float kmRecorridos;
    private Parada paradaInicio;
    private Parada paradaFin;
    private Tarifa tarifa;
    private Monopatin monopatin;
    private Usuario usuario;

}
