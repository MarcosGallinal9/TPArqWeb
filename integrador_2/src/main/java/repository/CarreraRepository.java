package repository;

import dto.CarreraDTO;

import java.util.List;

public interface CarreraRepository {
    void insertarCSV(String rutaArchivo);
    List<CarreraDTO> carrerasConEstudiantesOrdenadas();
    List<String> generarReporte();
}
