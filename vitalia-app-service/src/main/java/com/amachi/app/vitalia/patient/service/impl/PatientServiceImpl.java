package com.amachi.app.vitalia.patient.service.impl;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.hospital.repository.HospitalRepository;
import com.amachi.app.vitalia.patient.dto.search.PatientSearchDto;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.patient.repository.PatientRepository;
import com.amachi.app.vitalia.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;

    /**
     * Registra o actualiza la relación de un paciente con un hospital.
     * Si el paciente es nuevo, lo crea.
     * Si ya existe, simplemente actualiza su lista de hospitales y hospital principal si es necesario.
     *
     * @param patientData        Datos básicos del paciente (nombre, ID, etc.)
     * @param hospitalCode       Código del hospital donde se atiende
     * @return Patient actualizado o creado
     */
    @Transactional
    @Override
    public Patient registerPatientVisit(Patient patientData, String hospitalCode) {
        Hospital hospital = hospitalRepository.findByHospitalCode(hospitalCode)
                .orElseThrow(() -> new IllegalArgumentException("Hospital no encontrado"));

        // Buscar si ya existe el paciente por su documento de identidad
        Optional<Patient> existingOpt = patientRepository.findByIdCard(patientData.getIdCard());

        Patient patient;

        if (existingOpt.isPresent()) {
            patient = existingOpt.get();

            // Si no está relacionado con el hospital, lo agregamos
            if (!patient.getHospitales().contains(hospital)) {
                patient.getHospitales().add(hospital);
            }

            // Si no tiene hospital principal, se asigna este
            if (patient.getHospitalPrincipal() == null) {
                patient.setHospitalPrincipal(hospital);
            }

        } else {
            // Es nuevo, lo creamos
            patient = patientData;
            patient.setHospitalPrincipal(hospital);
            patient.getHospitales().add(hospital);
        }

        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAll() {
        return List.of();
    }

    @Override
    public Page<Patient> getAll(PatientSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public Optional<Patient> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Patient create(Patient entity) {
        return null;
    }

    @Override
    public Patient update(Long id, Patient entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
