package com.amachi.app.vitalia.medical.appointment.repository;

import com.amachi.app.core.common.enums.AppointmentStatus;
import com.amachi.app.vitalia.medical.appointment.entity.Appointment;
import com.amachi.app.core.common.repository.CommonRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Persistencia dinámica para citas médicas con Bloqueo Pesimista para alta concurrencia.
 * Implementa validación atómica de solapamientos (Critical Section).
 */
@Repository
public interface AppointmentRepository extends CommonRepository<Appointment, Long> {

    /**
     * Busca colisiones de citas para un médico en un rango de tiempo.
     * Usa LockModeType.PESSIMISTIC_WRITE para evitar Overbooking en condiciones de carrera.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COUNT(a) FROM Appointment a " +
           "WHERE a.doctor.id = :doctorId " +
           "AND a.status NOT IN :excludedStatus " +
           "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    long countOverlappingDoctor(@Param("doctorId") Long doctorId,
                                @Param("startTime") OffsetDateTime startTime,
                                @Param("endTime") OffsetDateTime endTime,
                                @Param("excludedStatus") List<AppointmentStatus> excludedStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COUNT(a) FROM Appointment a " +
           "WHERE a.patient.id = :patientId " +
           "AND a.status NOT IN :excludedStatus " +
           "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    long countOverlappingPatient(@Param("patientId") Long patientId,
                                 @Param("startTime") OffsetDateTime startTime,
                                 @Param("endTime") OffsetDateTime endTime,
                                 @Param("excludedStatus") List<AppointmentStatus> excludedStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COUNT(a) FROM Appointment a " +
           "WHERE a.room.id = :roomId " +
           "AND a.status NOT IN :excludedStatus " +
           "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    long countOverlappingRoom(@Param("roomId") Long roomId,
                              @Param("startTime") OffsetDateTime startTime,
                              @Param("endTime") OffsetDateTime endTime,
                              @Param("excludedStatus") List<AppointmentStatus> excludedStatus);
}
