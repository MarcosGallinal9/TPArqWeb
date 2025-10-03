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




}
