package com.amachi.app.vitalia.hospital.service;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalConfigService {

    @Value("${hospital.default.code}")
    private String defaultHospitalCode;

    private final HospitalRepository hospitalRepository;

    /**
     * Retorna el hospital configurado como predeterminado.
     */
    public Hospital getHospitalPorDefecto() {
        return hospitalRepository.findByHospitalCode(defaultHospitalCode)
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el hospital por defecto con código: " + defaultHospitalCode));
    }

    /**
     * Retorna el hospital configurado como predeterminado y activo en BD.
     */
    public Hospital getHospitalPorDefectoByActive() {
        return hospitalRepository.findByIsDefaultTrue()
                .orElseThrow(() -> new IllegalStateException("No hay hospital por defecto activo"));
    }
}
