package com.amachi.app.vitalia.medical.nurse.repository;

import com.amachi.app.vitalia.medical.nurse.entity.Nurse;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestion del personal de enfermeria.
 */
@Repository
public interface NurseRepository extends CommonRepository<Nurse, Long> {

    /**
     * Busca un enfermero/a por su matricula profesional.
     *
     * @param nurseLicense Matricula/Licencia.
     * @return El personal encontrado o vacio.
     */
    Optional<Nurse> findByNurseLicense(String nurseLicense);
}
