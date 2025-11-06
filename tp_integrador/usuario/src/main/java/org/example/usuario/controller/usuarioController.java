package org.example.usuario.controller;
import org.example.usuario.service.UsuarioService;
import org.example.usuario.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")

public class usuarioController {

        UsuarioService usuarioService;

        @GetMapping("/")
        public ResponseEntity<List<Usuario>> getUsuarios() {
            List<Usuario> usuarios = usuarioService.getAll();
            if (usuarios.isEmpty()) {
                return  ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(usuarios);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Usuario> getUserById(@PathVariable("id") String id) {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                return  ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
        }

        @PostMapping("")
        public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
            Usuario nuevoUsuario = usuarioService.save(usuario);
            return ResponseEntity.ok(nuevoUsuario);
        }



    }

