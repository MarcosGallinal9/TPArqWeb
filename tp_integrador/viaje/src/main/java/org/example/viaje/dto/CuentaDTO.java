package org.example.viaje.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CuentaDTO {
    public String id;
    public String tipoCuenta; //  "BASICA", "PREMIUM")
    public boolean estado;    // True = Activa, False = Anulada


    public boolean getEstado() {
        return this.estado;
    }
}
