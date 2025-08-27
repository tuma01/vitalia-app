package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HISTORIA_FAMILIAR")
public class HistoriaFamiliar implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INFORMACION_SALUD_MADRE", length = 200)
    private String informacionSaludMadre;

    @Column(name = "INFORMACION_SALUD_PADRE", length = 200)
    private String informacionSaludPadre;

    @Column(name = "INFORMACION_SALUD_HERMANOS", length = 200)
    private String informacionSaludHermanos;

    @Column(name = "INFORMACION_SALUD_ABUELOS", length = 200)
    private String informacionSaludAbuelos;

    @ElementCollection
    @CollectionTable(name = "ENFERMEDADES_HEREDITARIAS", joinColumns = @JoinColumn(name = "ID_HISTORIA_FAMILIAR"))
    @Column(name = "ENFERMEDAD")
    private List<String> enfermedadesHereditarias;

    @Column(name = "HISTORIAL_ENFERMEDADES_CARDIACAS_FAMILIARES", length = 200)
    private String historialEnfermedadesCardiacasFamiliares;

    @Column(name = "HISTORIAL_ENFERMEDADES_MENTALES_FAMILIARES", length = 200)
    private String historialEnfermedadesMentalesFamiliares;

    @Column(name = "FECHA_REGISTRO_HISTORIA_FAMILIAR", nullable = false, updatable = false)
    private LocalDateTime fechaRegistroHistoriaFamiliar;

    @Column(name = "FECHA_ACTUALIZACION_HISTORIA_FAMILIAR")
    private LocalDateTime fechaActualizacionHistoriaFamiliar;

    @Column(name = "INFORMACION_SALUD_HIJOS", length = 200)
    private String informacionSaludHijos;

    @Column(name = "INFORMACION_SALUD_OTROS_PARIENTES", length = 200)
    private String informacionSaludOtrosParientes;

    @Column(name = "HISTORIAL_DIABETES_FAMILIAR", length = 200)
    private String historialDiabetesFamiliar;

    @Column(name = "HISTORIAL_CANCER_FAMILIAR", length = 200)
    private String historialCancerFamiliar;

    @Column(name = "ETNIA_FAMILIA", length = 100)
    private String etniaFamilia;

    @Column(name = "NOTAS_ADICIONALES", length = 500)
    private String notasAdicionales;

    @PrePersist
    public void prePersist() {
        this.fechaRegistroHistoriaFamiliar = LocalDateTime.now();
        this.fechaActualizacionHistoriaFamiliar = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacionHistoriaFamiliar = LocalDateTime.now();
    }
}
