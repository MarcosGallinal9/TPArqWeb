package org.example.integrador_3.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "carrera")
@Data
@NoArgsConstructor
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarrera;

    private String carrera;
    private int duracion; // En años

    @OneToMany(mappedBy = "carrera")
    @JsonManagedReference(value = "carrera-estudiante")
    private List<EstudianteCarrera> estudiantesCarrera;
}
