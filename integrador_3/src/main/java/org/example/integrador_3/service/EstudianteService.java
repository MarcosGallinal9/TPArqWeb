package org.example.integrador_3.service;


import jakarta.transaction.Transactional;
import org.example.integrador_3.dto.EstudianteDTO;
import org.example.integrador_3.entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.integrador_3.repository.EstudianteRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteService {
    private  EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional
    public EstudianteDTO add(Estudiante estudiante) throws Exception {
        try{
            Estudiante saved= estudianteRepository.save(estudiante);
            return toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public List<EstudianteDTO> findAll() throws Exception {
        try{
            return estudianteRepository.findAll()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EstudianteDTO> findAllByOrderByEdad() throws Exception {
        try{
            return estudianteRepository.findAllByOrderByEdad()
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public EstudianteDTO getByNroLibreta(Integer nroLibreta) throws Exception {
        try{
            Estudiante encontrado= estudianteRepository.getByNroLibreta(nroLibreta);
            if(encontrado==null){
                throw new Exception("Nro libreta no encontrado");
            }
            return toDTO(encontrado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EstudianteDTO> getByGenero(String genero) {
        try{
            return estudianteRepository.getByGenero(genero)
                    .stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private EstudianteDTO toDTO(Estudiante estudiante){
        if(estudiante==null){
            return null;
        }
        return new EstudianteDTO(estudiante.getDni(), estudiante.getNombre(), estudiante.getApellido(), estudiante.getEdad(),
                estudiante.getGenero(), estudiante.getCiudadResidencia(), estudiante.getNroLibreta());
    }
}
