package com.amachi.app.vitalia.country.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents a country entity with ISO codes and related metadata.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "COUNTRY", uniqueConstraints = {
        @UniqueConstraint(name = "UK_ISO_COUNTRY", columnNames = {"ISO"}),
        @UniqueConstraint(name = "UK_NAME_COUNTRY", columnNames = {"NAME"})
})
public class Country extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotNull(message = "ISO code shouldn't be null")
    @Column(name = "ISO", nullable = false, length = 2, unique = true)
    private String iso;

    @NotBlank(message = "Name of Country cannot be empty")
    @Column(name = "NAME", nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank(message = "Nice Name of Country cannot be empty")
    @Column(name = "NICE_NAME", length = 100)
    private String niceName;

    @Column(name = "ISO3", length = 3)
    private String iso3;

    @Column(name = "NUM_CODE")
    private Integer numCode;

    @NotNull(message = "Phone code shouldn't be null")
    @Column(name = "PHONE_CODE", nullable = false)
    private Integer phoneCode;
}
