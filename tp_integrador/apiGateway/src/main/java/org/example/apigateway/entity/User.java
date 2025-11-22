package org.example.apigateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {

    private String id;

    private String nombre; // Usado como username

    private String contrasenia; // Debe guardar la contraseña ENCRIPTADA

    private String rol; // Nuevo campo para guardar el rol (ej. "ADMIN", "USER")


    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getRol() {
        return rol;
    }
}
