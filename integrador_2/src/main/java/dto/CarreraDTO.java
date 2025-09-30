package dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarreraDTO {
    private int id_carrera;
    private String carrera;
    private int duracion;

    public CarreraDTO(int id_carrera, String carrera, int duracion) {
        this.id_carrera = id_carrera;
        this.carrera = carrera;
        this.duracion = duracion;
    }

    public int getId_carrera() {
        return id_carrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getDuracion() {
        return duracion;
    }
}
