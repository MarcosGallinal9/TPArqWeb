package dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EstudianteCarreraDTO {
    private int id;
    private int id_estudiante; //DNI
    private int id_carrera;
    private int inscripcion;
    private int graduacion;
    private int antiguedad;

    public EstudianteCarreraDTO(int id, int id_estudiante, int id_carrera, int inscripcion, int graduacion, int antiguedad) {
        this.id = id;
        this.id_estudiante = id_estudiante;
        this.id_carrera = id_carrera;
        this.inscripcion = inscripcion;
        this.graduacion = graduacion;
        this.antiguedad = antiguedad;
    }

    public int getId() {
        return id;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public int getInscripcion() {
        return inscripcion;
    }

    public int getGraduacion() {
        return graduacion;
    }

    public int getAntiguedad() {
        return antiguedad;
    }
}
