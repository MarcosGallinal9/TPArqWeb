package org.example.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParadaDTO {
    private String id;
    private String direccion;
    private float latitud;
    private float longitud;
}
