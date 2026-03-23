package com.amachi.app.core.management.tenantconfig.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import com.amachi.app.core.common.enums.SubscriptionPlan;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDate;

@Validated
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "TenantConfig", description = "Advanced configuration and professional metadata for the Tenant ecosystem")
public class TenantConfigDto {

    @Valid
    @JsonProperty
    @Schema(description = "ID único de configuración", example = "1")
    private Long id;

    @Valid
    @JsonProperty
    @NotNull(message = "Tenant ID {err.required}")
    @Schema(description = "ID del Tenant asociado", example = "1")
    private Long tenantId;

    @Valid
    @JsonProperty
    @Schema(description = "Header personalizado para identificación", example = "X-Tenant-ID")
    private String fallbackHeader;

    @Valid
    @JsonProperty
    @Schema(description = "Permite autenticación local", example = "true")
    private Boolean allowLocal;

    @Valid
    @JsonProperty
    @Schema(description = "Dominio principal del Tenant", example = "vitalia.app")
    private String defaultDomain;

    @Valid
    @JsonProperty
    @Schema(description = "Configuración regional", example = "es-ES")
    private String locale;

    @Valid
    @JsonProperty
    @Schema(description = "Zona horaria operativa", example = "America/La_Paz")
    private String timezone;

    @Valid
    @JsonProperty
    @Min(value = 1, message = "Max Users {err.min_value}")
    @Schema(description = "Límite de usuarios permitidos", example = "100")
    private Integer maxUsers;

    @Valid
    @JsonProperty
    @Schema(description = "Cuota de almacenamiento en bytes", example = "10737418240")
    private Long storageQuotaBytes;

    @Valid
    @JsonProperty
    @Schema(description = "Habilita verificación de correo", example = "true")
    private Boolean requireEmailVerification;

    @Valid
    @JsonProperty
    @Schema(description = "Política de contraseñas (JSON)", example = "{\"minLength\":10}")
    private String passwordPolicyJson;

    @Valid
    @JsonProperty
    @Schema(description = "Configuraciones adicionales (JSON)", example = "{\"debug\":false}")
    private String extraJson;

    @Valid
    @JsonProperty
    @Schema(description = "Nombre comercial del Hospital", example = "Hospital San Rafael")
    private String tenantName;

    @Valid
    @JsonProperty
    @Schema(description = "Resumen institucional", example = "Líder regional en cuidados críticos")
    private String tenantDescription;

    // --- IDENTITY & BRANDING ---
    @Valid
    @JsonProperty
    @Schema(description = "URL del Logo Principal", example = "https://vitalia.com/logos/hosp1.png")
    private String logoUrl;

    @Valid
    @JsonProperty
    @Schema(description = "URL del Favicon", example = "https://vitalia.com/favicons/hosp1.ico")
    private String faviconUrl;

    @Valid
    @JsonProperty
    @Schema(description = "Eslogan Institucional", example = "Tecnología para la vida")
    private String slogan;

    @Valid
    @JsonProperty
    @Schema(description = "URL del Sello Institucional", example = "https://vitalia.com/seals/hosp1.png")
    private String sealUrl;

    // --- LEGAL & FISCAL ---
    @Valid
    @JsonProperty
    @Schema(description = "Razón Social Completa", example = "Sociedad Salud S.A.")
    private String legalName;

    @Valid
    @JsonProperty
    @Schema(description = "Número de Identificación Tributaria", example = "NIT-1234567")
    private String taxId;

    @Valid
    @JsonProperty
    @Schema(description = "Licencia Sanitaria General", example = "MS-BOL-99")
    private String medicalLicense;

    @Valid
    @JsonProperty
    @Schema(description = "Nombre del Director Médico", example = "Dr. Alberto Amachi")
    private String medicalDirector;

    @Valid
    @JsonProperty
    @Schema(description = "Matrícula del Director", example = "MD-5544")
    private String medicalDirectorLicense;

    // --- OPERATIONAL ---
    @Valid
    @JsonProperty
    @Schema(description = "Categoría o Nivel", example = "Hospital de Tercer Nivel")
    private String hospitalCategory;

    @Valid
    @JsonProperty
    @Schema(description = "Capacidad instalada (Camas)", example = "300")
    private Integer bedCapacity;

    @Valid
    @JsonProperty
    @Schema(description = "Quirófanos disponibles", example = "10")
    private Integer operatingRoomsCount;

    @Valid
    @JsonProperty
    @Schema(description = "Servicio de Emergencia 24/7", example = "true")
    private Boolean emergency247;

    // --- CONTACT ---
    @Valid
    @JsonProperty
    @Schema(description = "Teléfono de contacto público", example = "2-2441234")
    private String contactPhone;

    @Valid
    @JsonProperty
    @Email(message = "Email format {err.invalid}")
    @Schema(description = "Email de contacto público", example = "info@hospital.com")
    private String contactEmail;

    @Valid
    @JsonProperty
    @Schema(description = "URL del sitio web", example = "www.hospital.com")
    private String website;

    @Valid
    @JsonProperty
    @Schema(description = "Número de Fax", example = "2-2441235")
    private String faxNumber;

    @Valid
    @JsonProperty
    @Schema(description = "Número oficial de WhatsApp", example = "77766554")
    private String whatsappNumber;

    @Valid
    @JsonProperty
    @Schema(description = "Redes Sociales (JSON)", example = "{\"facebook\":\"hospital\", \"twitter\":\"@hospital\"}")
    private String socialLinks;

    // Métodos manuales para asegurar visibilidad al compilador
    public String getSocialLinks() { return socialLinks; }
    public void setSocialLinks(String socialLinks) { this.socialLinks = socialLinks; }

    @Valid
    @JsonProperty
    @Schema(description = "Ubicación física o dirección oficial")
    private com.amachi.app.core.geography.address.dto.AddressDto address;

    public com.amachi.app.core.geography.address.dto.AddressDto getAddress() { return address; }
    public void setAddress(com.amachi.app.core.geography.address.dto.AddressDto address) { this.address = address; }

    @Valid
    @JsonProperty
    @Schema(description = "Fecha de Inicio (Onboarding)", example = "2024-01-01")
    private LocalDate onboardingDate;

    @Valid
    @JsonProperty
    @Schema(description = "Fecha de Expiración del Contrato", example = "2025-01-01")
    private LocalDate expirationDate;
}
