package dto;

public class CarreraReporteDTO {
    private String carrera;
    private int anio;
    private long inscriptos;
    private long graduados;

    public CarreraReporteDTO(String carrera, int anio) {
        this.carrera = carrera;
        this.anio = anio;
    }

    public void setInscriptos(long inscriptos) {
        this.inscriptos = inscriptos;
    }

    public void setGraduados(long graduados) {
        this.graduados = graduados;
    }

    public String getCarrera() {
        return carrera;
    }

    public int getAnio() {
        return anio;
    }

    public long getInscriptos() {
        return inscriptos;
    }

    public long getGraduados() {
        return graduados;
    }
}
