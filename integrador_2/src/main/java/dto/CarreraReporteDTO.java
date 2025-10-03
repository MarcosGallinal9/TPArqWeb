package dto;


import lombok.Getter;


@Getter
public class CarreraReporteDTO {
    private String carrera;
    private Integer anio;
    private long inscriptos;
    private long graduados;

    public CarreraReporteDTO(String carrera, Integer anio, long cantidadInscriptos) {
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = cantidadInscriptos;
        this.graduados = 0;
    }

    // constructor usado para graduados (mismo tipo)
    public CarreraReporteDTO(String carrera, Integer anio, boolean isGraduados, long cantidad) {
        this.carrera = carrera;
        this.anio = anio;
        if (isGraduados) {
            this.graduados = cantidad;
            this.inscriptos = 0;
        } else {
            this.inscriptos = cantidad;
            this.graduados = 0;
        }
    }



    public void setInscriptos(long inscriptos) {
        this.inscriptos = inscriptos;
    }
    public void setGraduados(long graduados) {
        this.graduados = graduados;
    }

    @Override
    public String toString() {
        return "Carrera: " + carrera + " | Año: " + anio + " | Inscriptos: " + inscriptos + " | Graduados: " + graduados;
    }
}
