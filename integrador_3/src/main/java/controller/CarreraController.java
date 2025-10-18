package controller;

import entity.Carrera;
import entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import repository.CarreraRepository;
import service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/carrera")
public class CarreraController {
    @Qualifier
    @Autowired
    private CarreraService carreraService;

    public CarreraController(@Qualifier("CarreraService")CarreraService service) {}

    @GetMapping
    public List<Carrera> findAll() {
        return carreraService.findAll();
    }

    @GetMapping("/inscriptos")
    public List<Carrera> getCarrerasConInscriptosOrdenadas(){
        return carreraService.getCarrerasConInscriptosOrdenadas();
    }

}
