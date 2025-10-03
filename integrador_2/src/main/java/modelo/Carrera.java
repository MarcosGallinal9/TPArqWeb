package modelo;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Carrera")
@Data
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_carrera;

    private String carrera;
    private int duracion; // En años

    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<EstudianteCarrera> estudiantesCarrera;
}

