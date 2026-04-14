package com.amachi.app.core.geography.municipality.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.province.entity.Province;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GEO_MUNICIPALITY", uniqueConstraints = {
                @UniqueConstraint(name = "UK_MUNICIPALITY_NAME_PROVINCE", columnNames = { "NAME", "FK_ID_PROVINCE" })
})
public class Municipality extends Auditable<String> implements Model {

        @NotBlank(message = "{err.required}")
        @Column(name = "NAME", nullable = false, length = 100)
        private String name;

        @Column(name = "ADDRESS", length = 200)
        private String address;

        @Column(name = "MUNICIPALITY_CODE")
        private Integer municipalityCode;

        @Column(name = "POPULATION")
        @Schema(description = "Population of the municipality", example = "8000")
        private Integer population;

        @Column(name = "SURFACE")
        @Schema(description = "Surface area of the municipality", example = "123.5")
        private BigDecimal surface;

        @NotNull(message = "{err.mandatory}")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "FK_ID_PROVINCE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_MUNICIPALITY_PROVINCE"))
        private Province province;
}
