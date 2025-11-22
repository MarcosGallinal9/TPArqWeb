package org.example.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.example.apigateway.entity.User;
import org.example.apigateway.feignClients.UsuarioAuthFeignClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private UsuarioAuthFeignClient usuarioAuthFeignClient;

    public UserService(UsuarioAuthFeignClient usuarioAuthFeignClient) {
        this.usuarioAuthFeignClient = usuarioAuthFeignClient;
    }

    public Mono<UserDetails> findByUsername(final String username) { // *** CAMBIO: Retorna Mono<UserDetails> ***

        // Encapsulamos la llamada síncrona del Feign Client en un Mono
        // para cumplir con la interfaz reactiva.
        return Mono.fromCallable(() -> {
            org.example.apigateway.entity.User user = usuarioAuthFeignClient.getUsuarioAuthData(username);

            if (user == null) {
                throw new UsernameNotFoundException("Usuario " + username + " no encontrado en MS Usuario.");
            }

            List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRol()));

            return new org.springframework.security.core.userdetails.User(
                    user.getNombre(),
                    user.getContrasenia(),
                    grantedAuthorities
            );
        });
    }
}
