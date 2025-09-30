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
    private int id;

    private int id_estudiante;
    private int id_carrera;
    private Year inscripcion;
    private Year graduacion;
    private int antiguedad; //En años
}
