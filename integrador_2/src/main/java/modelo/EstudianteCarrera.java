package modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;

@Entity
@Table(name = "EstudianteCarrera")
@Data
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // ID autogenerado para la relación

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    private Year inscripcion;
    private Year graduacion;
    private int antiguedad; // en años



}
