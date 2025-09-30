package dto;

import java.time.Year;

public class CarreraReporteDTO {
    private String carrera;
    private Year anio;
    private long inscriptos;
    private long graduados;

    // Constructor usado por la consulta de JPQL
    public CarreraReporteDTO(String carrera, Year anio, long cantidad) {
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = cantidad;
        this.graduados = 0;
    }

    // Constructor vacío para uso interno
    public CarreraReporteDTO(String carrera, Year anio) {
        this.carrera = carrera;
        this.anio = anio;
        this.inscriptos = 0;
        this.graduados = 0;
    }


    public String getCarrera() {
        return carrera;
    }
    public Year getAnio() {
        return anio;
    }
    public long getInscriptos() {
        return inscriptos;
    }
    public long getGraduados() {
        return graduados;
    }


    public void setInscriptos(long inscriptos) {
        this.inscriptos = inscriptos;
    }
    public void setGraduados(long graduados) {
        this.graduados = graduados;
    }

    @Override
    public String toString() {
        return "Carrera: " + carrera +
                " | Año: " + anio +
                " | Inscriptos: " + inscriptos +
                " | Graduados: " + graduados;
    }
}
