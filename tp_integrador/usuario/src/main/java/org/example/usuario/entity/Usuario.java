package org.example.usuario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

    public class Usuario {
        @Id
        private Long id;
        private String nombre;
        private String  mail;
        private int celular;
        private String rol;
        private List<Integer> cuentas; //me devuelve una lista con los id de esas cuentas
        private List<Integer> monopatines;
        private List<Integer> viajes;
    }


