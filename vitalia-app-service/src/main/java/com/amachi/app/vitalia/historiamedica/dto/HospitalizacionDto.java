package com.amachi.app.vitalia.historiamedica.dto;

import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "Hospitalizacion", description = "Representa un registro de hospitalización del paciente, detallando la información de ingreso, tratamiento y alta.")
public class HospitalizacionDto {

    @Schema(description = "Identificador único para el registro de hospitalización.", example = "12345")
    private Long id;

    @Schema(description = "Fecha y hora del ingreso del paciente al hospital.", example = "2023-10-26T09:00:00Z")
    private LocalDateTime fechaIngreso;

    @Schema(description = "Fecha y hora del alta del paciente del hospital.", example = "2023-11-01T15:30:00Z")
    private LocalDateTime fechaEgreso;

    @Schema(description = "Motivo principal del ingreso del paciente al hospital.", example = "Neumonía severa; Apendicitis; Paro cardíaco")
    private String motivoIngreso;

    @Schema(description = "Motivo del alta del paciente (por ejemplo, recuperación, traslado, alta planificada).", example = "Recuperación total; Traslado a centro de cuidados a largo plazo; Solicitud del paciente")
    private String motivoEgreso;

    @Schema(description = "Diagnóstico oficial realizado durante la hospitalización.", example = "Neumonía adquirida en la comunidad; Apendicitis aguda; Infarto de miocardio")
    private String diagnostico;

    @Schema(description = "Descripción del tratamiento médico y procedimientos administrados durante la hospitalización.", example = "Antibióticos intravenosos, oxigenoterapia; Apendicectomía; Angioplastia")
    private String tratamiento;

    @Schema(description = "Cualquier nota adicional u observación importante relacionada con la hospitalización.", example = "El paciente respondió bien al tratamiento; Requirió monitoreo continuo; Complicaciones durante la recuperación")
    private String observaciones;

    @Schema(description = "Referencia a la habitación específica asignada al paciente durante esta hospitalización. Contiene detalles como el número de habitación, tipo, etc.",
            example = "12L")
    private Long roomId; // Esto asume que RoomDto contiene los detalles básicos de la habitación


    @Schema(description = "Referencia a la visita global del paciente durante la cual ocurrió esta hospitalización. Esta visita proporciona el contexto del paciente, el hospital y el historial médico general.",
            example = "98765L") // Ejemplo de ID o de DTO anidado
    private Long patientVisitId;


    // --- Campos Adicionales Necesarios ---

    @Schema(description = "Referencia al departamento hospitalario específico donde el paciente fue hospitalizado. Proporciona detalles sobre la especialidad, ubicación, etc. de la unidad.",
            example = "ID del Departamento: 1, Nombre: Medicina Interna")
    private DepartamentoHospitalDto departamentoHospital; // Referencia a tu DTO del departamento


    @Schema(description = "Cualquier procedimiento, cirugía o intervención significativa realizada durante la hospitalización.",
            example = "Apendicectomía; Tomografía computarizada; Biopsia; Endoscopia")
    private String procedimientosRealizados;

    @Schema(description = "Lista de medicamentos recetados al paciente al momento del alta, incluyendo dosis y frecuencia.",
            example = "[\"Amoxicilina 500mg 3 veces al día\", \"Ibuprofeno 200mg según sea necesario\"]")
    private List<String> medicamentosAlAlta;

    @Schema(description = "Instrucciones dadas al paciente para el cuidado post-alta, citas de seguimiento o signos de alarma.",
            example = "Seguimiento con médico de cabecera en 1 semana; Mantener incisión limpia y seca; Volver si hay fiebre o aumento del dolor.")
    private String instruccionesPlanAlta;

    @Schema(description = "Condición clínica del paciente al momento del alta (por ejemplo, estable, mejorado, sin cambios).",
            example = "Estable; Mejorado; Sin cambios; Fallecido")
    private String condicionAlAlta;

    @Schema(description = "Cualquier complicación que haya surgido durante la hospitalización.",
            example = "Infección nosocomial; Reacción alérgica a la medicación; Sangrado post-quirúrgico")
    private String complicacionesDuranteEstancia;

    @Schema(description = "Un breve resumen de los hallazgos del examen físico del paciente al ingreso.",
            example = "Presión arterial: 120/80, Frecuencia cardíaca: 75 lpm, Pulmones: Claros, sin sibilancias.")
    private String resumenExamenFisicoIngreso;

    @Schema(description = "Tipo de admisión (por ejemplo, Urgencia, Electiva, Traslado desde otra instalación).",
            example = "Urgencia; Electiva; Traslado")
    private String tipoAdmision;

    @Schema(description = "Referencia al paciente asociado a esta hospitalización. Contiene información básica del paciente como nombre, identificación, etc.",
            example = "44L")
    private Long patientId;
}
