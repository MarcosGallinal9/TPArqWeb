package org.example.chat.controller;

import org.example.chat.service.GroqService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prueba/chat")
public class ChatWebController {
    private final GroqService groqService;

    public ChatWebController(GroqService groqService) {
        this.groqService = groqService;
    }

    @GetMapping
    public String mostrarPagina() {
        // Busca el archivo preguntas.html en resources/templates
        return "preguntas";
    }

    @PostMapping
    public String enviarPregunta(@RequestParam String pregunta, Model model) {

        // ID HARDCODEADO PARA PRUEBAS:
        String idUsuarioPrueba = "6914e611dd44076bd184eadc";

        // Llamado al servicio real (que usará las tools y el contexto del usuario)
        String respuesta = groqService.getGroqResponse(pregunta, idUsuarioPrueba);

        // Agrega la respuesta al modelo para que Thymeleaf la muestre en el HTML
        model.addAttribute("respuesta", respuesta);

        // Retorna la misma vista para mostrar el resultado
        return "preguntas";
    }
}
