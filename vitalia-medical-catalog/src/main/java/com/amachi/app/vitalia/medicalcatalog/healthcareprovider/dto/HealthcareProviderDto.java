package com.amachi.app.vitalia.medicalcatalog.healthcareprovider.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "HealthcareProvider", description = "Schema to hold Healthcare Provider (Payer/Insurance) information")
public class HealthcareProviderDto {

    @Schema(description = "Identificador único", example = "1")
    private Long id;

    @NotBlank(message = "Provider Code cannot be empty")
    @Size(max = 20, message = "Code must be at most 20 characters")
    @Schema(description = "Official Provider Code", example = "EPS001")
    private String code;

    @NotBlank(message = "Provider Name cannot be empty")
    @Size(max = 200, message = "Name must be at most 200 characters")
    @Schema(description = "Provider Name", example = "SURA EPS")
    private String name;

    @NotBlank(message = "Tax ID cannot be empty")
    @Size(max = 50, message = "Tax ID must be at most 50 characters")
    @Schema(description = "Tax Identification Number (NIT/RUT/DNI)", example = "860066942-7")
    private String taxId;

    @Email(message = "Invalid email format")
    @Schema(description = "Official Contact Email", example = "contact@sura.com")
    private String email;

    @Schema(description = "Official Contact Phone", example = "+573000000000")
    private String phone;

    @Schema(description = "Status of the provider", example = "true")
    private Boolean active;
}
