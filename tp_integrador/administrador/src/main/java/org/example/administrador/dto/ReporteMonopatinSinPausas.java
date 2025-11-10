package org.example.administrador.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteMonopatinSinPausas extends ReporteMonopatinXKm{
    private Long tiempoDeUsoNeto; //Tiempo de uso SIN pausas

    public ReporteMonopatinSinPausas(String id, Long kmRecorridos, Long tiempoDeUsoNeto) {
        super(id, kmRecorridos);
        this.tiempoDeUsoNeto = tiempoDeUsoNeto;
    }


}
