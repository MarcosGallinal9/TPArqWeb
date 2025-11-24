package org.example.chat.controller;

import org.example.chat.service.GroqService;
import org.example.chat.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GroqService groqChat;
    private final JwtUtil jwtUtils; // Inyectamos la utilidad de tokens

    public ChatController(GroqService groqChat, JwtUtil jwtUtils) {
        this.groqChat = groqChat;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/consultar")
    public ResponseEntity<String> preguntar(@RequestBody String pregunta,
                                            @RequestHeader("Authorization") String token) {

        // 1. Validar que el token venga
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido o ausente");
        }

        try {
            // 2. Extraer ID (quitando "Bearer " que son 7 caracteres)
            String idUsuario = jwtUtils.extractUsername(token.substring(7));

            // 3. Pasar el ID y la pregunta al servicio REAL
            String respuesta = groqChat.getGroqResponse(pregunta, idUsuario);

            // 4. Devolver la respuesta de la IA
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error de autorización o token inválido: " + e.getMessage());
        }
    }
}