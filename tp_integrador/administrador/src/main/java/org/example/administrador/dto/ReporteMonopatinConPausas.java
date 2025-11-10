package org.example.administrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ReporteMonopatinConPausas extends ReporteMonopatinXKm{
    private Long tiempoDeUsoTotal;

    public ReporteMonopatinConPausas(String id, Long kmRecorridos, Long tiempoDeUsoTotal) {
        super(id, kmRecorridos);
        this.tiempoDeUsoTotal = tiempoDeUsoTotal;
    }


}
