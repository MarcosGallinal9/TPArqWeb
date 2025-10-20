package org.example.integrador_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private int idCarrera;
    private String carrera;
    private int duracion;



    @Override
    public String toString() {
        return idCarrera + " - " + carrera + " (" + duracion + " años)";
    }
}