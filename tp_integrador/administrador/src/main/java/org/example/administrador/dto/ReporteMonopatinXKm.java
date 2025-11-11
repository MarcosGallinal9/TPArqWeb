package org.example.administrador.dto;

import lombok.*;

@Data
@AllArgsConstructor // <-- Generará el constructor con los 3 campos
@NoArgsConstructor
@Getter
@Setter
public class ReporteMonopatinXKm {
    private String id;
    private Long kmRecorridos;
    private Long tiempoUsoSegundos;
}
