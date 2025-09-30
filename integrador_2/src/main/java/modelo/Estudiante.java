package modelo;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Estudiante")
@Data
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dni;

    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int LU; //Libreta universitaria
}
