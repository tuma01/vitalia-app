package com.amachi.app.vitalia.medical.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * Esquema Profesional Elite para la gestión de Unidades y Pabellones Hospitalarios.
 * Soporta jerarquía organizacional, control de capacidad y roles administrativos.
 */
@Validated
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "DepartmentUnit", description = "Unidad u Operativa Hospitalaria (Pabellón/Servicio)")
public class DepartmentUnitDto {

    @Schema(description = "Identificador único de la unidad", example = "601")
    private Long id;

    @Schema(description = "Identificador Global (FHIR/Publico)", example = "UNIT-123-PEDIATRIA")
    private String externalId;

    @NotBlank(message = "Código {err.mandatory}")
    @Schema(description = "Código funcional de la unidad", example = "UCI-01")
    private String code;

    @NotBlank(message = "Nombre {err.mandatory}")
    @Schema(description = "Nombre oficial de la sección o pabellón", example = "CUIDADOS INTENSIVOS ADULTOS")
    private String name;

    @NotNull(message = "Tipo Unidad {err.mandatory}")
    @Schema(description = "ID del tipo de especialidad de la unidad", example = "501")
    private Long unitTypeId;

    @Schema(description = "Nombre del tipo de unidad (Solo lectura)", example = "UCI")
    private String unitTypeName;

    // --- Jerarquía ---
    @Schema(description = "ID de la unidad superior (Padre)", example = "600")
    private Long parentUnitId;

    @Schema(description = "Nombre de la unidad superior", example = "DEPARTAMENTO DE MEDICINA CRÍTICA")
    private String parentUnitName;

    // --- Responsable ---
    @Schema(description = "ID del jefe o responsable (Empleado)", example = "4001")
    private Long headOfEmployeeId;

    @Schema(description = "Nombre completo del responsable (Solo lectura)", example = "DR. PEREZ")
    private String headOfEmployeeName;

    // --- Operativo ---
    @Schema(description = "Capacidad máxima física de la unidad", example = "20")
    private Integer maxCapacity;

    @Schema(description = "Identifica si la unidad es de carácter clínico", example = "true")
    private Boolean isClinical;

    @Schema(description = "Piso o nivel físico", example = "3")
    private String floor;

    @Schema(description = "Teléfono o extensión de contacto", example = "EXT-304")
    private String contactPhone;

    @Schema(description = "Descripción adicional de la ubicación", example = "Ala Norte, cerca del ascensor 2")
    private String description;

    @Schema(description = "Indicador de operatividad en el sistema", example = "true")
    private Boolean active;
}
