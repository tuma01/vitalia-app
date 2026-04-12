package com.amachi.app.vitalia.medical.appointment.repository;

import com.amachi.app.vitalia.medical.appointment.entity.AppointmentReminder;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

/**
 * Persistencia para recordatorios de citas.
 */
@Repository
public interface AppointmentReminderRepository extends CommonRepository<AppointmentReminder, Long> {
}
