package org.example.apigateway.Controller;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.security.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Importación Reactiva
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autenticacion")
public class JWTController {

    // Al ser 'final', Lombok los inyecta en el constructor generado
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public JWTController(ReactiveAuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestParam String username, @RequestParam String password) {

        // 1. Intenta autenticar al usuario usando el manager reactivo.
        // Usamos .block() para obtener el resultado síncronamente.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        ).block(); // *** CAMBIO: Uso de .block() ***

        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 2. Si la autenticación es exitosa, obtiene los detalles y roles
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // ... (El resto de la lógica para generar el JWT se mantiene igual) ...

        // Extrae los roles como lista de Strings para el token
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 3. Genera el token JWT
        String token = jwtUtil.createToken(userDetails.getUsername(), roles);

        // 4. Construye la respuesta
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", userDetails.getUsername());

        // Opcional: Puedes devolver el token en un header o en el cuerpo.
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}