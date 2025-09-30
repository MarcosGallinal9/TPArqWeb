package modelo;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Estudiante")
@Data
public class Estudiante {
    @Id
    @Column(name = "dni")
    private int dni;   // El DNI es la PK

    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudadResidencia;
    private String nroLibreta;  // número de libreta universitaria

    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> carreras;
}
