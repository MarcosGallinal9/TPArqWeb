package org.example.usuario.controller;
import org.example.usuario.service.UsuarioService;
import org.example.usuario.entity.Usuario;

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
        public ResponseEntity<Usuario> getUserById(@PathVariable("id") Long id) {
            Usuario usuario = usuarioService.getUserById(id);
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
        @GetMapping("/monopatines/{usuarioId}")
        public ResponseEntity<List<Monopatin>> getMonopatinesByUserId(@PathVariable("usuarioId") Long usuarioId) {
            Usuario usuario = usuarioService.getUserById(usuarioId);

            if(usuario == null){
                return  ResponseEntity.notFound().build();
            }
            List<Monopatin> monopatines = usuarioService.getMonopatines(usuarioId);
            return ResponseEntity.ok(monopatines);
        }

        @PostMapping("/guardarMonopatin/{usuarioId}")
        public ResponseEntity<monopatin> guardarMonopatin(@PathVariable("usuarioId") Long userId, @RequestBody Monopatin monopatin) {
            if(usuarioService.getUserById(usuarioId) == null){
                return  ResponseEntity.notFound().build();
            }
            Monopatin monopatin = usuarioService.guardarMonopatin(usuarioId, monopatin);
            return ResponseEntity.ok(monopatin);
        }

        @GetMapping("/getAll/{userId}")
        public ResponseEntity<Map<String, Object>> getUsuarioYMonopatines(@PathVariable("usuarioId") Long usuarioId) {
            Map<String, Object> result = usuarioService.getUsuarioYMonopatines(usuarioId);
            return ResponseEntity.ok(result);
        }

    }
}
