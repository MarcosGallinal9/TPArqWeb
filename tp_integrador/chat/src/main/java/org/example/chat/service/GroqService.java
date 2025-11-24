package org.example.chat.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroqService {
    private ChatClient chatModel;


    public GroqService(ChatClient.Builder chatClientBuilder,
                       TimeService timeService,
                       DatosUsuarioService datosUsuarioService) {

        this.chatModel = chatClientBuilder
                .defaultSystem("Eres un asistente de atención al cliente para una app de alquiler de monopatines eléctricos. " +
                        "Responde de forma amable y concisa.")
                // Herramientas para que la IA las vea
                .defaultTools(timeService, datosUsuarioService)
                .build();
    }


    public String getGroqResponse(String userPrompt, String userId) {
        return this.chatModel.prompt()
                .system(s -> s.text("Eres un asistente útil. " +
                                "El ID del usuario actual es {userId}. " +
                                "Si una función requiere el ID del usuario, UTILIZA ESTE VALOR AUTOMÁTICAMENTE. " +
                                "No le preguntes el ID al usuario.")
                        .param("userId", userId)) // Contexto dinámico
                .user(userPrompt)
                .call()
                .content();
    }
}
