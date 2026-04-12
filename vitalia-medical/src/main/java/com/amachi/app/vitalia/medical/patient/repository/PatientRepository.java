package com.amachi.app.vitalia.medical.patient.repository;

import com.amachi.app.vitalia.medical.patient.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la gestion de pacientes.
 * Permite la busqueda por identificadores nacionales y clinicos.
 */
@Repository
public interface PatientRepository extends CommonRepository<Patient, Long> {

    /**
     * Busca un paciente por su Numero de Historia Clinica (NHC).
     *
     * @param nhc Numero de Historia Clinica.
     * @return El paciente encontrado o vacio.
     */
    Optional<Patient> findByNhc(String nhc);

    /**
     * Busca un paciente por su cedula o documento de identidad.
     *
     * @param idCard Cedula/DNI.
     * @return El paciente encontrado o vacio.
     */
    Optional<Patient> findByIdCard(String idCard);

    /**
     * Busca un paciente por su Identificador Global Interinstitucional.
     *
     * @param globalPatientId UUID global.
     * @return El paciente encontrado o vacío.
     */
    Optional<Patient> findByGlobalPatientId(String globalPatientId);

    /**
     * Busca un paciente por su correo electrónico.
     *
     * @param email Correo electrónico.
     * @return El paciente encontrado o vacío.
     */
    Optional<Patient> findByEmail(String email);

    /**
     * Busca pacientes por nombre parcial o apellido parcial.
     *
     * @param nombre Nombre a buscar.
     * @param apellidoPaterno Apellido paterno a buscar.
     * @param pageable Paginación.
     * @return Página de pacientes.
     */
    Page<Patient> findByNombreContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCase(String nombre, String apellidoPaterno, Pageable pageable);
}
