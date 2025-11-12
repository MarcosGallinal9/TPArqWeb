package org.example.administrador.feingClients;


import org.example.administrador.dto.UsuarioUsoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "microservicio-usuario", url = "http://localhost:8081/usuarios")
public interface UsuarioFeingClient {

    /**
     * GET obtiene la lista de todos los usuarios
     * @return lista de usuarios
     */
    @GetMapping("/")
    List<UsuarioUsoDTO> getUsuarios();

    /**
     * GET Obtiene un usuario por id
     * @param id
     * @return usuario por id
     */
    @GetMapping("/{id}")
    UsuarioUsoDTO getUsuarioById(@PathVariable("id") String id);
}
