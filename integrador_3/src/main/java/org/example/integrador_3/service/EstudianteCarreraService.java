package org.example.integrador_3.service;

import org.example.integrador_3.dto.EstudianteCarreraDTO;
import org.example.integrador_3.entity.Carrera;
import org.example.integrador_3.entity.Estudiante;
import org.example.integrador_3.entity.EstudianteCarrera;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.example.integrador_3.repository.CarreraRepository;
import org.example.integrador_3.repository.EstudianteCarreraRepository;
import org.example.integrador_3.repository.EstudianteRepository;
import java.util.List;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class EstudianteCarreraService {
    private EstudianteCarreraRepository estudianteCarreraRepository;
    private CarreraRepository carreraRepository;
    private EstudianteRepository estudianteRepository;

    public EstudianteCarreraService(EstudianteCarreraRepository estudianteCarreraRepository, CarreraRepository carreraRepository, EstudianteRepository estudianteRepository) {
        this.estudianteCarreraRepository = estudianteCarreraRepository;
        this.carreraRepository = carreraRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional
    public EstudianteCarreraDTO matricular(Long dniEstudiante, Long idCarrera) throws Exception {
        try{
            Estudiante estudiante = estudianteRepository.findById(dniEstudiante).orElseThrow(()-> new RuntimeException("Estudiante no encontrado" + idCarrera));
            Carrera carrera = carreraRepository.findById(idCarrera).orElseThrow(()-> new RuntimeException("Carrera no encontrada" + idCarrera));
        EstudianteCarrera ec = new EstudianteCarrera();
        ec.setEstudiante(estudiante);
        ec.setCarrera(carrera);
        ec.setInscripcion(LocalDate.now().getYear());
        ec.setAntiguedad(0);

        EstudianteCarrera saved = estudianteCarreraRepository.save(ec);
        return toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public List<EstudianteCarreraDTO> getByCarrera(Long carrera) throws Exception {
        try{
            return estudianteCarreraRepository.findByCarrera_IdCarrera(carrera)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EstudianteCarreraDTO> getByCarreraYCiudad(Long idCarrera, String ciudad) throws Exception{
        try{
            String nombreCarrera = carreraRepository.findById(idCarrera)
                    .map(Carrera::getCarrera)
                    .orElseThrow(() -> new RuntimeException("Carrera no encontrada" + idCarrera));
            return estudianteCarreraRepository.getByCarreraYCiudad(obtenerNombreCarreraPorId(idCarrera), ciudad)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private String obtenerNombreCarreraPorId(Long idCarrera) {
        return carreraRepository.findById(idCarrera)
                .map(Carrera::getCarrera)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));
    }

    private EstudianteCarreraDTO toDTO(EstudianteCarrera estudianteCarrera) {
        if(estudianteCarrera == null) {
            return null;
        }
        int idEstudiante= estudianteCarrera.getEstudiante() != null ? estudianteCarrera.getEstudiante().getDni() : 0;
        int idCarrera = estudianteCarrera.getCarrera() != null ? estudianteCarrera.getCarrera().getIdCarrera() :0;
        Integer inscripcion= estudianteCarrera.getInscripcion() != null ? estudianteCarrera.getInscripcion():0;
        Integer graduacion = estudianteCarrera.getGraduacion() != null ? estudianteCarrera.getGraduacion():0;
        return new EstudianteCarreraDTO(estudianteCarrera.getId(), idEstudiante, idCarrera, inscripcion, graduacion, estudianteCarrera.getAntiguedad());
    }

}
