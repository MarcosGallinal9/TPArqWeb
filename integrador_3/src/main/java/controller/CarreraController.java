package controller;

import entity.Carrera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.CarreraRepository;
import service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/carrera")
public class CarreraController {
    @Qualifier
    @Autowired
    private CarreraRepository carreraRepository;

    public CarreraController(@Qualifier("CarreraRepository")CarreraRepository repository) {}

    @GetMapping("/carreras")
    public List<Carrera> carreras() {
        return carreraRepository.findAll();
    }

}
