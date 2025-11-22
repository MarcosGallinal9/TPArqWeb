package org.example.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys; // Nuevo import
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "ClaveSecretaSuperLargaAbcdefghijk123456789";
    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .setSigningKey(key) // Usa la variable Key
                .build()           // Nuevo método obligatorio
                .parseClaimsJws(token);
    }

    public String createToken(String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 86400000L; // 24 horas (como en el ejemplo de la cátedra)

        return Jwts.builder()
                .setSubject(subject) // Nombre de usuario
                .claim("roles", roles) // Roles/Autoridades del usuario
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(key) // Firma con la clave secreta
                .compact();
    }
}
