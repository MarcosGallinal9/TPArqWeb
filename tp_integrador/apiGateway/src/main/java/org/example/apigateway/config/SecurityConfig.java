package org.example.apigateway.config;

import org.example.apigateway.security.AuthorityConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Importaciones Reactivas:
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
// No se usa @EnableWebSecurity ni @EnableMethodSecurity en Spring Cloud Gateway
// El manejo de JWT se hace mediante tu GlobalFilter.
@Configuration
public class SecurityConfig {

    // Ya no se requiere la inyección de JwtUtil aquí, ya que el filtro JWT Global (JwtAuthFilter)
    // es quien maneja el token y agrega la info del rol a la solicitud para que ServerHttpSecurity la use.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        // Esta clase es la versión reactiva del DaoAuthenticationProvider
        var authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authManager.setPasswordEncoder(passwordEncoder);
        return authManager;
    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        // Configuramos ServerHttpSecurity, que es la versión reactiva.
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Deshabilitar CSRF (común en APIs REST)

                .authorizeExchange(auth -> auth
                        // Permite acceso libre a autenticación y registro de usuarios
                        .pathMatchers(HttpMethod.POST, "/autenticacion").permitAll()
                        .pathMatchers(HttpMethod.POST, "/usuarios").permitAll()

                        // Rutas de ADMIN: /administrador/**, /cuentas/**, etc.
                        .pathMatchers("/administrador/**", "/cuentas/**", "/facturas/**", "/tarifas/**", "/usuarios/**").hasAuthority(AuthorityConstant._ADMIN)

                        // Rutas de USER (incluye las no protegidas como monopatin y parada)
                        .pathMatchers("/viajes/**").hasAuthority(AuthorityConstant._USER)
                        .pathMatchers("/monopatines/**", "/paradas/**").hasAnyAuthority(AuthorityConstant._USER, AuthorityConstant._ADMIN)

                        // Cualquier otra petición debe estar autenticada
                        .anyExchange().authenticated()
                )
                .build();
    }


}
