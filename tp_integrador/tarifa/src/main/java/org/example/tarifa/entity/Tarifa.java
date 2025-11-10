package org.example.tarifa.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "tarifas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarifa {
    @Id
    private String id;
    private int valorComun;
    private int valorPremium;
    private int valorExtraPausa;
}

//En una cuenta premium, el usuario paga un valor
//mensual fijo, y tienen recorridos ilimitados hasta 100 km por mes, y luego de eso paga el 50% de la tarifa,
//hasta que se renueva su cupo el mes siguiente.

//El Administrador es quien gestiona los monopatines y las paradas en la aplicación (por ej., agregando,
//quitando, actualizando datos según sea requerido), también establece los precios de tarifa normal y
//extras por reinicio de pausas extensas.