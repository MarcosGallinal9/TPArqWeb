package org.example.viaje.repository;

import org.example.viaje.dto.ReporteMonopatinContadorViajes;
import org.example.viaje.dto.reporteUsoDto;
import org.example.viaje.entity.Viaje;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ViajeRepository extends MongoRepository<Viaje,String> {
    List<Viaje> findByIdUsuario(Long idUsuario);

    /**
     * Realiza una agregación para contar los viajes finalizados por monopatín en un año específico.
     *
     * @param year Año a filtrar.
     */
    @Aggregation(pipeline = {

            //Agrupa por el ID del monopatín (idMonopatin)
            "{ $group: { " +
                    "'_id': '$idMonopatin', " + // Agrupa por el campo idMonopatin
                    "'cantidadViajes': { $sum: 1 } " + // Suma 1 por cada documento en el grupo
                    "} }",

            // Mapea los campos del grupo al DTO
            "{ $project: { " +
                    "'idMonopatin': '$_id', " + // Renombra _id (campo de agrupación) a idMonopatin (campo del DTO)
                    "'cantidadViajes': 1, " +
                    "'_id': 0 " + // Oculta el campo _id por defecto
                    "} }"
    })
    List<ReporteMonopatinContadorViajes> contadorViajesXAnio(int year);


    //punto h
    @Query("SELECT new org.example.viaje.dto.ReporteUsoDto(" +
            "  SUM(v.tiempoUso), SUM(v.tiempoConPausa), SUM(v.kilometros) ) " +
            "FROM Viaje v " +
            "WHERE v.usuarioId IN ?1 AND v.fechaInicio BETWEEN ?2 AND ?3")
    reporteUsoDto tiempoUsoUsuario(List<String> userIds, LocalDate fechaInicio, LocalDate fechaFin);
}