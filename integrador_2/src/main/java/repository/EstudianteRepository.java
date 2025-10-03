package repository;

import dto.EstudianteDTO;
import java.util.List;

public interface EstudianteRepository {
    void insertarCSV(String rutaArchivo);
    EstudianteDTO darAltaEstudiante(int dni, String nombre, String apellido, int edad, String genero, String ciudad, int LU);
    List<EstudianteDTO> obtenerEstudiantesOrdenados();
    EstudianteDTO estudiantePorLU(int libretaUniversitaria);
    List<EstudianteDTO> obtenerEstudiantesXGenero(String genero);
    List<EstudianteDTO> listarEstudiantes(String carrera , String ciudad);
}

