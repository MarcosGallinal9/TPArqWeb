package repository;

import dto.EstudianteDTO;
import modelo.Carrera;
import modelo.Estudiante;

import java.util.List;

public interface EstudianteCarreraRepository {
    void insertarCSV(String rutaArchivo);
    void matricularEstudiante(Estudiante estudiante, Carrera carrera);


}
