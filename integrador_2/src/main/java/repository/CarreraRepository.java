package repository;


import dto.CarreraInscriptosDTO;
import java.util.List;

public interface CarreraRepository {
    void insertarCSV(String rutaArchivo);
    List<CarreraInscriptosDTO> carrerasConEstudiantesOrdenadas();
    List<String> generarReporte();
}

