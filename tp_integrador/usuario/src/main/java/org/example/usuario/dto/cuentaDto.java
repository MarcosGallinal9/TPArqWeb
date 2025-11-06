package org.example.usuario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class cuentaDto {
    public Long id;
    public String tipoCuenta; //  "BASICA", "PREMIUM")
    public boolean estado;    // True = Activa, False = Anulada


    public boolean getEstado() {
        return this.estado;
    }
}
