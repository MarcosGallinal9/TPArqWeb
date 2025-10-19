package service;

import dto.EstudianteCarreraDTO;
import entity.Carrera;
import entity.Estudiante;
import entity.EstudianteCarrera;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CarreraRepository;
import repository.EstudianteCarreraRepository;
import repository.EstudianteRepository;
import java.util.List;

import java.time.LocalDate;

@Service
public class EstudianteCarreraService implements BaseService<EstudianteCarrera>{
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Transactional
    public EstudianteCarrera matricular(Long dniEstudiante, Long idCarrera) {
        Estudiante estudiante = estudianteRepository.findById(dniEstudiante).orElseThrow(()-> new RuntimeException("Estudiante no encontrado" + dniEstudiante));
        Carrera carrera = carreraRepository.findById(idCarrera).orElseThrow(()-> new RuntimeException("Carrera no encontrada" + idCarrera));
        EstudianteCarrera ec= new EstudianteCarrera();
        ec.setEstudiante(estudiante);
        ec.setCarrera(carrera);
        ec.setInscripcion(LocalDate.now().getYear());
        ec.setAntiguedad(0);
        return estudianteCarreraRepository.save(ec);
    }

    public List<EstudianteCarrera> getByCarrera(Long carrera) {
        return estudianteCarreraRepository.getByCarrera_IdCarrera(carrera);
    }

    public List<EstudianteCarrera> getByCarreraYCiudad(Long idCarrera, String ciudad) {
        return estudianteCarreraRepository.getByCarreraYCiudad( obtenerNombreCarreraPorId(idCarrera), ciudad);
    }
    private String obtenerNombreCarreraPorId(Long idCarrera) {
        return carreraRepository.findById(idCarrera)
                .map(Carrera::getCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
    }


}
