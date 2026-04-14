package com.amachi.app.core.geography.province.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.state.entity.State;
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
@Table(name = "GEO_PROVINCE", uniqueConstraints = {
                @UniqueConstraint(name = "UK_PROVINCE_NAME_STATE", columnNames = { "NAME", "FK_ID_STATE" })
})
public class Province extends Auditable<String> implements Model {

        @NotBlank(message = "{err.required}")
        @Column(name = "NAME", nullable = false, length = 100)
        private String name;

        @Column(name = "POPULATION")
        @Schema(description = "Population of the province", example = "8000")
        private Integer population;

        @Column(name = "SURFACE")
        @Schema(description = "Surface area of the province", example = "123.5")
        private BigDecimal surface;

        @NotNull(message = "{err.mandatory}")
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "FK_ID_STATE", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_PROVINCE_STATE"))
        private State state;
}
