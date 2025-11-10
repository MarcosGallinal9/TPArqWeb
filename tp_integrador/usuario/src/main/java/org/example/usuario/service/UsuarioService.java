package org.example.usuario.service;

import org.example.usuario.dto.cuentaDto;
import org.example.usuario.dto.reporteUsoDto;
import org.example.usuario.entity.Usuario;
import org.example.usuario.dto.monopatinDto;
import org.example.usuario.feignClients.monopatinFeignClient;
import org.example.usuario.repository.UsuarioRepository;
import org.example.usuario.feignClients.cuentaFeignClient;
import org.example.usuario.feignClients.viajeFeignClient;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    cuentaFeignClient cuentaFeignClient;
    monopatinFeignClient monopatinFeignClient;
    viajeFeignClient viajeFeignClient;

    public UsuarioService(UsuarioRepository usuarioRepository, cuentaFeignClient cuentaFeignClient, monopatinFeignClient monopatinFeignClient, viajeFeignClient viajeFeignClient) {
        this.usuarioRepository = usuarioRepository;
        this.cuentaFeignClient = cuentaFeignClient;
        this.monopatinFeignClient = monopatinFeignClient;
        this.viajeFeignClient = viajeFeignClient;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }

    public Usuario findById(String id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario regristrarUsuario(Usuario usuario, String nroCuenta) {
        cuentaDto cuentaAsociar =  cuentaFeignClient.getCuenta(nroCuenta);
        if (cuentaAsociar == null) {
            throw new RuntimeException();
        }
        if (usuario.getCuentas() == null) {
            usuario.setCuentas(new ArrayList<>());
        }
        usuario.getCuentas().add(nroCuenta);
        return usuarioRepository.save(usuario);
    }

    public List<monopatinDto> buscarMonopatinesCercanos(double lat, double lng, double radiokm) {
        return monopatinFeignClient.getMonopatinesCercanos(lat, lng, radiokm);
    }

    public reporteUsoDto getReporteUso(String userId, LocalDate fechaInicio, LocalDate fechaFin, boolean otrosUsuarios) {
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
        List<String> usuariosConsultar = new ArrayList<>();
        usuariosConsultar.add(userId);
        //si eligio la opcion de cuentas vinculadas
        if (otrosUsuarios){
            //recorre las cuentas del usuario
            for (String nroCuenta: usuario.getCuentas()) {
                //obtiene los ids asociados
                List<String> usuariosRelacionados = cuentaFeignClient.getUsuariosAsociados(nroCuenta);
               //añade los usuarios a la lista final
                for (String asociadoId: usuariosRelacionados) {
                    usuariosConsultar.add(asociadoId);
                }
            }
        }
        return viajeFeignClient.getReporteUso(usuariosConsultar, fechaInicio, fechaFin);
    }



}
