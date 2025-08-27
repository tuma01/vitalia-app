package com.amachi.app.vitalia.patient.dto;

import com.amachi.app.vitalia.address.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(name = "Insurance", description = "Schema to hold Insurance information")
public class InsuranceDto {

    @Schema(description = "Unique identifier of the insurance", example = "1")
    private Long id;

    @Schema(description = "Insurance number", example = "123456789")
    private Integer insuranceNumber;

    @Schema(description = "Name of the insurance company", example = "Seguro de vida Libertad")
    private String name;

    @Schema(description = "Telephone number of the insurance company", example = "+1234567890")
    private String telephone;

    @Schema(description = "Fax number of the insurance company", example = "+0987654321")
    private String fax;

    @Schema(description = "Website of the insurance company", example = "https://www.segurodelibertad.com")
    private String webSite;

    @Schema(description = "Date when the insurance was issued", example = "2023-01-01")
    private LocalDate insuranceDate;

    @Schema(description = "Expiration date of the insurance", example = "2024-01-01")
    private LocalDate expirationDate;

    @Schema(description = "Address of the insurance company")
    private AddressDto address;

    @Schema(description = "Contact person at the insurance company", example = "John Doe")
    private String contactPerson;

    @Schema(description = "Contact email of the insurance company", example = "contactemail@insurance.com")
    private String contactEmail;

    @Schema(description = "Policy type of the insurance", example = "Health Insurance")
    private String policyType;

    @Schema(description = "Details of the insurance policy", example = "Comprehensive health coverage including hospitalization and outpatient services")
    private String policyDetails;
}
