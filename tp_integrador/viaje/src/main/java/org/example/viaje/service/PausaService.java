package org.example.viaje.service;

import org.example.viaje.entity.Pausa;
import org.example.viaje.feignClients.TarifaFeignClient;
import org.example.viaje.repository.PausaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class PausaService {
    PausaRepository pausaRepository;

    TarifaFeignClient tarifaFeignClient;

    public PausaService(PausaRepository pausaRepository) {
        this.pausaRepository = pausaRepository;
    }
    public Pausa save(Pausa pausa){
        return pausaRepository.save(pausa);
    }
    public Pausa findById(String idViaje){
        return pausaRepository.findById(idViaje).orElse(null);
    }
//    public Pausa findByIdViaje(String idViaje){
//        return pausaRepository.findByIdViaje(idViaje);
//    }

    /**
     * Inicia una nueva pausa asociada a un viaje.
     * @param idViaje ID del viaje.
     * @return La Pausa registrada.
     */
    public Pausa iniciarPausa(String idViaje) {
        Pausa pausa = new Pausa();
        pausa.setIdViaje(idViaje);
        pausa.setInicio(new Date());
        return pausaRepository.save(pausa);
    }

    /**
     * Finaliza una pausa y registra el tiempo total.
     * @param idPausa ID de la pausa a finalizar.
     * @return La Pausa actualizada.
     */
    public Pausa finalizarPausa(String idPausa) {
        Pausa pausa = pausaRepository.findById(idPausa).orElseThrow(() -> new RuntimeException("Pausa no encontrada."));

        // 1. Registrar fin de pausa
        pausa.setFin(new Date());

        // 2. Calcular la duración en minutos
        long diffInMillis = pausa.getFin().getTime() - pausa.getInicio().getTime();
        long durationMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);

        // 3. Aplicar lógica de multa si excede los 15 minutos
        if (durationMinutes > 15) {
            //falta logica para la multa cuando la pausa es mayor a 15 mint
            System.out.println("ADVERTENCIA: Pausa del viaje " + pausa.getIdViaje() + " ha excedido los 15 minutos (" + durationMinutes + " min).");

        }

        return pausaRepository.save(pausa);
    }
}
