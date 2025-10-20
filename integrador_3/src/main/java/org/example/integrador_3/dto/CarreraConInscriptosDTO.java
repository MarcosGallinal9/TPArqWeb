package org.example.integrador_3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraConInscriptosDTO {
    private Integer idCarrera;
    private String carrera;
    private Integer duracion;
    private Long inscriptos;
}