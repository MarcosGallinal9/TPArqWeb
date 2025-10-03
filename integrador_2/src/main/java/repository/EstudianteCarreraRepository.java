package repository;



public interface EstudianteCarreraRepository {
    void insertarCSV(String rutaArchivo);
    void matricularEstudiante(int dniEstudiante, int idCarrera);
}
