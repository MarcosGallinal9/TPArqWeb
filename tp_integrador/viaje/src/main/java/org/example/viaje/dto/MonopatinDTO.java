package org.example.viaje.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MonopatinDTO {
    private String id;
    private String estado;
    private float latitud;
    private float longitud;
    private float kmRecorridos;
    private long tiempoUso; //En minutos
    private String idParadaUbicacion;

//    public MonopatinDTO(String id, String estado, float latitud, float longitud, float kmRecorridos, long tiempoUso, String idParadaUbicacion) {
//        this.id = id;
//        this.estado = estado;
//        this.latitud = latitud;
//        this.longitud = longitud;
//        this.kmRecorridos = kmRecorridos;
//        this.tiempoUso = tiempoUso;
//        this.idParadaUbicacion = idParadaUbicacion;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public float getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(float kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public long getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(long tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public String getIdParadaUbicacion() {
        return idParadaUbicacion;
    }

    public void setIdParadaUbicacion(String idParadaUbicacion) {
        this.idParadaUbicacion = idParadaUbicacion;
    }
}
