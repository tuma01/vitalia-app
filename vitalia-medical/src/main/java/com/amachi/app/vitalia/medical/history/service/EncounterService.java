package com.amachi.app.vitalia.medical.history.service;

import com.amachi.app.vitalia.medical.history.dto.search.EncounterSearchDto;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.history.dto.request.ConditionRequest;
import com.amachi.app.vitalia.medical.history.dto.request.ObservationRequest;
import com.amachi.app.vitalia.medical.history.dto.request.StartEncounterRequest;
import com.amachi.app.vitalia.medical.history.entity.Condition;
import com.amachi.app.vitalia.medical.history.entity.Encounter;
import com.amachi.app.vitalia.medical.history.entity.MedicationRequest;
import com.amachi.app.vitalia.medical.history.entity.Observation;

/**
 * Interfaz de servicio clínico de grado hospitalario (FHIR Tier).
 * Orquesta el acto médico y la integridad clínica de los datos.
 */
public interface EncounterService extends GenericService<Encounter, EncounterSearchDto> {

    /**
     * Inicia un encuentro clínico validando check-in logístico.
     */
    Encounter startEncounter(StartEncounterRequest request);

    /**
     * Reanuda un encuentro en pausa (ON_HOLD).
     */
    Encounter resumeEncounter(Long encounterId);

    /**
     * Pausa temporalmente un encuentro (ej: espera de ex├ímen).
     */
    Encounter holdEncounter(Long encounterId, String reason);

    /**
     * Finaliza el encuentro validando diagn├│stico y profesional.
     */
    Encounter completeEncounter(Long encounterId);

    /**
     * Cancela el encuentro si no ha sido finalizado.
     */
    Encounter cancelEncounter(Long encounterId, String reason);

    /**
     * Registro transaccional de diagn├│stico (CIE-10).
     */
    Condition addCondition(Long encounterId, ConditionRequest request);

    /**
     * Registro transaccional de medici├│n cl├¡nica (LOINC).
     */
    Observation addObservation(Long encounterId, ObservationRequest request);

    /**
     * Generaci├│n de receta o prescripci├│n m├⌐dica vinculada al acto cl├¡nico.
     */
    MedicationRequest prescribeMedication(Long encounterId, MedicationRequest medicationRequest);
}
