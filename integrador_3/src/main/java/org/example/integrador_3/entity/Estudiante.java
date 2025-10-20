package org.example.integrador_3.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "estudiante")
@Data
@NoArgsConstructor
public class Estudiante {
    @Id
    @Column(name = "dni")
    private int dni;   // El DNI es la PK

    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudadResidencia;
    private int nroLibreta;  // número de libreta universitaria

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<EstudianteCarrera> carreras;
}
