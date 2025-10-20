package org.example.integrador_3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteDTO {
    private int dni; //Primary key
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int LU; //Libreta universitaria



    @Override
    public String toString() {
        return dni + " - " + nombre + " " + apellido + " | " + edad + " | " + genero + " | " + ciudad + " | LU: " + LU;
    }


}