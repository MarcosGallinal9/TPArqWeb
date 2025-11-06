package org.example.cuenta.Service;

import org.example.cuenta.Repository.CuentaRepository;
import org.example.cuenta.entity.Cuenta;
import entity.Usuario;

import java.util.List;

public class CuentaService {

    CuentaRepository cuentaRepository;


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

    public Cuenta getUserById(Long id){
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta update(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public List<Usuario> getMonopatines(Long id) {
        return cuentaRepository.findById(id);
    }

}
