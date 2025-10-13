package controller;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

@PostMapping //Actualizar todo un recurso

@PatchMapping//Actualizar parte de un recurso
public void changeEmail(){

}
}
