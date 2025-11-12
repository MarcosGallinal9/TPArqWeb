package org.example.usuario.controller;
import org.example.usuario.dto.monopatinDto;
import org.example.usuario.dto.reporteUsoDto;
import org.example.usuario.service.UsuarioService;
import org.example.usuario.entity.Usuario;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/usuarios")

public class usuarioController {

        UsuarioService usuarioService;

    public usuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

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


        @PostMapping("/registrar/")
        public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario, @RequestParam("nroCuenta") String nroCuenta){
            Usuario nuevoUsuario = usuarioService.regristrarUsuario(usuario, nroCuenta);
            return ResponseEntity.ok(nuevoUsuario);
        }


        //g. Como usuario quiero buscar un listado de los monopatines cercanos a mi zona, para poder
        //encontrar un monopatín cerca de mi ubicación

    @GetMapping("/cercanos")
    public ResponseEntity<List<monopatinDto>> getMonopatinesCercanos(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("radiokm") double radiokm){
        List<monopatinDto> monopatines = usuarioService.buscarMonopatinesCercanos(lat, lng, radiokm);

        if (monopatines == null || monopatines.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monopatines);
    }

        @GetMapping("/{userId}/reporteUso")
        public ResponseEntity<reporteUsoDto> getReporteUso(@PathVariable("userId") String userId, @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate fechaFin, @RequestParam("otrosUsuarios") boolean otrosUsuarios){
            reporteUsoDto reporte = usuarioService.getReporteUso(userId, fechaInicio, fechaFin, otrosUsuarios);
            return ResponseEntity.ok(reporte);

        }


    }

