package org.example.cuenta.Controller;

import org.example.cuenta.entity.Cuenta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.cuenta.Service.CuentaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuenta")

public class CuentaController {


    CuentaService cuentaService;

    @GetMapping("/")
    public ResponseEntity<List<Cuenta>> getCuentas() {
        List<Cuenta> cuentas = cuentaService.getAll();
        if (cuentas.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable("id") String id) {
        Cuenta cuenta = cuentaService.getByUserId(id);
        if (cuenta == null) {
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cuenta);
    }

    @PostMapping("")
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.save(cuenta);
        return ResponseEntity.ok(nuevaCuenta);
    }

//    @GetMapping("/cuentas/{usuarioId}")
//    public ResponseEntity<List<Cuenta>> getCuentas(@PathVariable("usuarioId") Long usuarioId) {
//        Usuario usuario = cuentaService.getCuentaById(usuarioId);
//
//        if(usuario == null){
//            return  ResponseEntity.notFound().build();
//        }
//        List<Cuenta> cuentas = cuentaService.getCuentas(usuarioId);
//        return ResponseEntity.ok(cuentas);
//    }
//
//    @PostMapping("/guardarCuenta/{usuarioId}")
//    public ResponseEntity<Cuenta> guardarCuenta(@PathVariable("usuarioId") Long userId, @RequestBody Cuenta cuenta) {
//        if(usuarioService.getUserById(usuarioId) == null){
//            return  ResponseEntity.notFound().build();
//        }
//        Cuenta cuenta = cuentaService.guardarCuenta(usuarioId, cuenta);
//        return ResponseEntity.ok(cuenta);
//    }
//
//    @GetMapping("/getAll/{userId}")
//    public ResponseEntity<Map<String, Object>> getUsuarioYCuentas(@PathVariable("usuarioId") Long usuarioId) {
//        Map<String, Object> result = cuentaService.getUsuarioYMonopatines(usuarioId);
//        return ResponseEntity.ok(result);
//    }

}

