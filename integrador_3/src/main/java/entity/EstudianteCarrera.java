package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiante_carrera")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante")
    @JsonBackReference(value = "estudiante-carrera")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    @JsonBackReference(value = "carrera-estudiante")
    private Carrera carrera;

    private Integer inscripcion;
    private Integer graduacion;
    private int antiguedad; // en años


}
