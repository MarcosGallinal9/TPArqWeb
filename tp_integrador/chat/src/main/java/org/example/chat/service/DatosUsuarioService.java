package org.example.chat.service;

import org.example.chat.utils.JwtUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DatosUsuarioService {

    private final RestClient restClient;
    private final JwtUtil jwtUtil;

    public DatosUsuarioService(RestClient.Builder builder, JwtUtil jwtUtil) {
        this.restClient = builder.baseUrl("http://localhost:8080").build();
        this.jwtUtil = jwtUtil;
    }

    @Tool(name = "historialViajes", description = "Obtiene el historial de viajes realizados por un usuario dado su ID.")
    public String obtenerHistorialViajes(String idUsuario) {
        try {

            String token = jwtUtil.createToken(idUsuario, "USER");

            return restClient.get()
                    .uri("/viajes/byUser/" + idUsuario)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            e.printStackTrace(); // Verás el error real en la consola de IntelliJ
            return "Error al consultar viajes: " + e.getMessage();
        }
    }

    @Tool(name = "infoCuentas", description = "Obtiene el estado de la cuenta y saldo del usuario dado su ID.")
    public String obtenerInfoCuentas(String idUsuario) {
        try {
            String token = jwtUtil.createToken(idUsuario, "USER");

            return restClient.get()
                    .uri("/cuenta/by-user/" + idUsuario)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al consultar cuenta: " + e.getMessage();
        }
    }

    @Tool(name = "listarMonopatines", description = "Obtiene la lista de todos los monopatines disponibles, su ubicación y estado.")
    public String listarMonopatines() {
        try {
            // Para listar monopatines
            String token = jwtUtil.createToken("admin-system", "ADMIN");

            return restClient.get()
                    .uri("/monopatines")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al listar monopatines: " + e.getMessage();
        }
    }
}
