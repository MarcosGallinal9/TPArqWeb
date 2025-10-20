package org.example.integrador_3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraReporteDTO {
    private String nombreCarrera;
    private Integer anio;
    private Long inscriptos;
    private Long egresados;

}
