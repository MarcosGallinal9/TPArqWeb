package modelo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carrera")
@Data
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_carrera;

    private String carrera;
    private int duracion; //En años
}
