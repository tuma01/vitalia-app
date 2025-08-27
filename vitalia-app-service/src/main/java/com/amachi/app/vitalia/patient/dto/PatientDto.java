package com.amachi.app.vitalia.patient.dto;


import com.amachi.app.vitalia.common.utils.BloodGroup;
import com.amachi.app.vitalia.common.utils.PatientStatus;
import com.amachi.app.vitalia.country.dto.CountryDto;
import com.amachi.app.vitalia.nurse.dto.NurseDto;
import com.amachi.app.vitalia.room.dto.RoomDto;
import com.amachi.app.vitalia.user.dto.PersonDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Patient", description = "Schema to hold Patient information, extending Person")
public class PatientDto extends PersonDto {

    @Schema(description = "Número de identificación del paciente", example = "A123456789")
    @Size(max = 100, message = "El número de identificación no debe exceder 100 caracteres")
    private String idCard;

    @Schema(description = "Estado actual del paciente", example = "ACTIVE")
    @NotNull(message = "El estado del paciente es obligatorio")
    private PatientStatus patientStatus;

    @Schema(description = "Ocupación del paciente", example = "Ingeniero de Sistemas")
    @Size(max = 100)
    private String occupation;

    @Schema(description = "Nacionalidad del paciente", example = "Canadiense")
    @Size(max = 100)
    private String nationality;

    @Schema(description = "Grado de instrucción del paciente", example = "Universitaria completa")
    @Size(max = 100)
    private String degreeOfInstruction;

    @Schema(description = "País de nacimiento del paciente")
    private CountryDto countryOfBirth;

    @Schema(description = "Habitación asignada al paciente")
    private RoomDto room;

    @Schema(description = "Fotografía del paciente en formato byte[]")
    private byte[] photo;

    @Schema(description = "Seguro médico del paciente")
    private InsuranceDto insurance;

    @Schema(description = "Enfermera asignada al paciente")
    private NurseDto nurse;

    @Schema(description = "Observaciones adicionales sobre el paciente")
    @Size(max = 500)
    private String additionalRemarks;

    @Schema(description = "Hábitos tóxicos del paciente")
    private HabitoToxicoDto habitoToxico;

    @Schema(description = "Hábitos fisiológicos del paciente")
    private HabitoFisiologicoDto habitoFisiologico;

    @Schema(description = "Historial familiar del paciente")
    private Long historiaFamiliarId;

    @Schema(description = "Lista de visitas del paciente")
    private Set<Long> visitsIds;

    @Schema(description = "Grupo sanguíneo del paciente", example = "O_POS")
    private BloodGroup bloodGroup;

    @Schema(description = "Peso del paciente en kilogramos", example = "72.50")
    @Digits(integer = 3, fraction = 2, message = "El peso debe ser un número válido con hasta 2 decimales")
    private BigDecimal weight;

    @Schema(description = "Altura del paciente en metros", example = "1.75")
    @Digits(integer = 3, fraction = 2, message = "La altura debe ser un número válido con hasta 2 decimales")
    private BigDecimal height;

    @Schema(description = "Información de contacto de emergencia del paciente")
    private EmergencyContactDto emergencyContact;

    @Schema(description = "Lista de hospitalizaciones del paciente")
    private Set<Long> hospitalizacionesIds;

    @Schema(description = "Fecha de registro del paciente", example = "2024-07-01T14:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistroPaciente;

    @Schema(description = "Fecha de última actualización del paciente", example = "2024-07-10T16:45:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaActualizacionPaciente;

    @Schema(description = "Indica si el paciente tiene alguna discapacidad", example = "true")
    private Boolean tieneDiscapacidad;

    @Schema(description = "Detalles sobre la discapacidad, si aplica", example = "Discapacidad visual parcial")
    @Size(max = 250)
    private String detallesDiscapacidad;

    @Schema(description = "Indica si el paciente está embarazada", example = "false")
    private Boolean embarazada;

    @Schema(description = "Número de semanas de embarazo (solo si está embarazada)", example = "24")
    @Min(1)
    @Max(45)
    private Integer semanasEmbarazo;

    @Schema(description = "Número total de hijos que tiene el paciente", example = "3")
    @Min(0)
    private Integer numeroHijos;

    @Schema(description = "Grupo étnico al que pertenece el paciente", example = "Indígena")
    @Size(max = 100)
    private String grupoEtnico;
}
