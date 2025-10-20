package controller;

import dto.CarreraReporteDTO;
import entity.Carrera;
import entity.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.CarreraRepository;
import service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("api/carrera")
public class CarreraController {

    private final CarreraService carreraService;
    @Autowired
    public CarreraController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public List<Carrera> findAll() {
        return carreraService.findAll();
    }

    @GetMapping("/inscriptos")
    public List<Carrera> getCarrerasConInscriptosOrdenadas(){
        return carreraService.getCarrerasConInscriptosOrdenadas();
    }

    @GetMapping("/reportes")
    public List<CarreraReporteDTO> generarReporteCarreras(){
        return carreraService.generarReporteCarreras();
    }

    @PostMapping("/crearCarrera")
    public Carrera crearCarrera(@RequestBody Carrera carrera) {
        return carreraService.add(carrera);
    }


}
