package org.example.integrador_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EstudianteCarreraDTO {
    private int id;
    private int idEstudiante; //DNI
    private int idCarrera;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;




}