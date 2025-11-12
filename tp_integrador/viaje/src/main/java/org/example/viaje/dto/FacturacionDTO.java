package org.example.viaje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDTO {
    private Date fecha;
    private String viajeId;
    private String usuarioId;
    private double total;
}
