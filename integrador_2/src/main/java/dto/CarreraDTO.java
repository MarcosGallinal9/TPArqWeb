package dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private int id_carrera;
    private String carrera;
    private int duracion;



    @Override
    public String toString() {
        return id_carrera + " - " + carrera + " (" + duracion + " años)";
    }
}
