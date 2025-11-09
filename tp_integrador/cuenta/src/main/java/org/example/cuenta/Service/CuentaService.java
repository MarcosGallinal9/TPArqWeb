package org.example.cuenta.Service;

import org.example.cuenta.Repository.CuentaRepository;
import org.example.cuenta.entity.Cuenta;

import java.util.List;

public class CuentaService {

    CuentaRepository cuentaRepository;

    public CuentaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public List<Cuenta> getAll(){
        return cuentaRepository.findAll();
    }

    public Cuenta save(Cuenta cuenta){
        Cuenta nuevaCuenta;
        nuevaCuenta = cuentaRepository.save(cuenta);
        return nuevaCuenta;
    }
    public void delete(Cuenta cuenta){
        cuentaRepository.delete(cuenta);
    }

    public Cuenta getByUserId(String id){
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta update(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public Cuenta findById(String id){
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta cargarSaldo(String id, double saldo){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        double montoActual = cuenta.getMonto();
        cuenta.setMonto(montoActual + saldo);
        return cuentaRepository.save(cuenta);
    }

    public List<String> getUsuariosAsociados(String id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);
        return cuenta.getUsuarios();
    }
}
