package com.amachi.app.vitalia.medical.employee.dto;

import com.amachi.app.core.common.enums.EmployeeStatus;
import com.amachi.app.core.common.enums.EmployeeType;
import com.amachi.app.core.domain.dto.PersonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema(name = "Employee", description = "Esquema que contiene la información detallada de un empleado (Médico, Administrativo, etc.)")
public class EmployeeDto extends PersonDto {

    @NotNull(message = "El tipo de empleado es obligatorio")
    @Schema(description = "Tipo de empleado", example = "MEDICO")
    private EmployeeType employeeType;

    @NotNull(message = "El estado del empleado es obligatorio")
    @Schema(description = "Estado laboral del empleado", example = "ACTIVO")
    private EmployeeStatus employeeStatus;

    @Size(max = 150)
    @Schema(description = "Profesión del empleado", example = "Médico General")
    private String profesion;

    @Size(max = 120)
    @Schema(description = "Puesto de trabajo", example = "Jefe de Servicio")
    private String puesto;

    @Size(max = 120)
    @Schema(description = "Cargo administrativo", example = "Director Médico")
    private String cargo;

    @Size(max = 50)
    @Schema(description = "Número de colegiado médico", example = "CM-12345")
    private String nroColegioMedico;

    @Size(max = 50)
    @Schema(description = "Matrícula profesional", example = "MP-67890")
    private String matriculaProfesional;

    @NotBlank(message = "El código de empleado es obligatorio")
    @Size(max = 20)
    @Schema(description = "Código interno del empleado", example = "EMP-001")
    private String codigoEmpleado;

    @Schema(description = "Fecha de ingreso al hospital", example = "2024-01-15")
    private LocalDate fechaIngreso;

    @Schema(description = "Fecha de salida o cese", example = "2025-01-15")
    private LocalDate fechaSalida;

    @Schema(description = "ID del usuario asociado para login", example = "10")
    private Long fkIdUser;
}
