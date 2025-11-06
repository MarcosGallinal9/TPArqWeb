package org.example.administrador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.SequencedCollection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonopatinDTO<L extends SequencedCollection<E>> {
    private String id;
    private String estado;
    private float latitud;
    private float longitud;
    private float kmRecorridos;
    private long tiempoUso; //En minutos
    private String idParadaUbicacion;




}
