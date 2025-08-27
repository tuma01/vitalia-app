package com.amachi.app.vitalia.patient.entity;

import com.amachi.app.vitalia.address.entity.Address;
import com.amachi.app.vitalia.common.entities.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
@Entity
@Table(name = "INSURANCE")
public class Insurance implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "INSURANCE_NUMBER")
    private Integer insuranceNumber;

    @NotNull(message = "Name code shouldn't be null")
    @Column(name = "NAME")
    @Schema(
            description = "Name of Insurance company", example = "Seguro de vida Libertad"
    )
    private String name;

    @Column(name = "TELEPHONE", length = 50)
    private String telephone;

    @Column(name = "FAX", length = 50)
    private String fax;

    @Column(name = "WEB_SITE", length = 100)
    private String webSite;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "INSURANCE_DATE", length = 10)
    private LocalDate insuranceDate;

    @JsonFormat(pattern = "yyyy-MM-dd`")
    @Column(name = "EXPIRATION_DATE", length = 10)
    private LocalDate expirationDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_ID_ADDRESS", foreignKey = @ForeignKey(name = "FK_INSURANCE_ADDRESS"), referencedColumnName = "ID")
    private Address address;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @Column(name = "POLICY_TYPE")
    private String policyType;

    @Column(name = "POLICY_DETAILS")
    private String policyDetails;
}
