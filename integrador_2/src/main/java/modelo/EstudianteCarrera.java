package modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "EstudianteCarrera")
@Data
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    private Integer inscripcion;
    private Integer graduacion;
    private int antiguedad; // en años
}

