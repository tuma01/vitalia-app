package com.amachi.app.vitalia.hospital.controller;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.hospital.service.HospitalConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospital-config")
@RequiredArgsConstructor
public class HospitalConfigController {

    private final HospitalConfigService hospitalConfigService;

    @GetMapping("/default")
    public ResponseEntity<Hospital> getHospitalPorDefecto() {
        Hospital hospital = hospitalConfigService.getHospitalPorDefecto();
        return ResponseEntity.ok(hospital);
    }

    @GetMapping("/active")
    public ResponseEntity<Hospital> getHospitalPorDefectoByActive() {
        Hospital hospital = hospitalConfigService.getHospitalPorDefectoByActive();
        return ResponseEntity.ok(hospital);
    }
}
