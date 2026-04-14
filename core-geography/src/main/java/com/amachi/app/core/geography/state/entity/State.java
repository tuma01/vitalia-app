package com.amachi.app.core.geography.state.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.country.entity.Country;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
    name = "GEO_STATE",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_STATE_NAME_COUNTRY", columnNames = {"NAME", "FK_ID_COUNTRY"})
    }
)
public class State extends Auditable<String> implements Model {

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "POPULATION")
    private Integer population;

    @Column(name = "SURFACE")
    private BigDecimal surface;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_COUNTRY", nullable = false,
                foreignKey = @ForeignKey(name = "FK_STATE_COUNTRY"))
    private Country country;

    @PrePersist
    @PreUpdate
    private void normalize() {
        if (this.name != null) {
            this.name = this.name.trim().toUpperCase();
        }
    }
}
