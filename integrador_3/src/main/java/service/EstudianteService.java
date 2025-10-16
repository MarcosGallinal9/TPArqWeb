package service;


import entity.Estudiante;
import entity.EstudianteCarrera;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.EstudianteRepository;


import java.util.Collections;
import java.util.List;
@Service
public class EstudianteService implements BaseService<Estudiante>{
    @Autowired
    private  EstudianteRepository estudianteRepository;


    public Estudiante add(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> findAll(){
        return estudianteRepository.findAll();
    }

    public List<Estudiante> findAllByOrderByEdad(){
        return estudianteRepository.findAllByOrderByEdad();

    }

    public Estudiante getByNroLibreta(Integer nroLibreta) {
        return estudianteRepository.getByNroLibreta(nroLibreta);
    }

    public List<Estudiante> getByGenero(String genero) {
        return estudianteRepository.getByGenero(genero);
    }
}
