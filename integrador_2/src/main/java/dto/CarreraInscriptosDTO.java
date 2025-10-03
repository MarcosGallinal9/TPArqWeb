package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraInscriptosDTO {
    private String carrera;
    private long inscriptos;


    @Override
    public String toString() {
        return carrera + " | Inscriptos: " + inscriptos;
    }
}
