package org.example.viaje.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TarifaDTO {
    private String id;
    private int valorComun;
    private int valorPremium;
    private int valorExtrapausa;

}
