package com.amachi.app.core.geography.address.entity;

import com.amachi.app.core.common.entity.Auditable;
import com.amachi.app.core.common.entity.Model;
import com.amachi.app.core.geography.country.entity.Country;
import com.amachi.app.core.geography.state.entity.State;
import com.amachi.app.core.geography.province.entity.Province;
import com.amachi.app.core.geography.municipality.entity.Municipality;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GEO_ADDRESS")
public class Address extends Auditable<String> implements Model {

    @Column(name = "STREET_NUMBER", length = 100)
    private String streetNumber;

    @Column(name = "STREET_NAME", length = 100)
    private String streetName;

    @Column(name = "BLOCK", length = 50)
    private String block;

    @Column(name = "FLOOR")
    private Integer floor;

    @Column(name = "APARTMENT_NUMBER", length = 20)
    private String apartmentNumber;

    @Column(name = "METER_NUMBER", length = 20)
    private String meterNumber;

    @Column(name = "ZIP_CODE", length = 20)
    private String zipCode;

    @Column(name = "CITY", length = 100)
    private String city;

    @Column(name = "LOCATION", length = 100)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_COUNTRY", foreignKey = @ForeignKey(name = "FK_ADDRESS_COUNTRY"))
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_STATE", foreignKey = @ForeignKey(name = "FK_ADDRESS_STATE"))
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_PROVINCE", foreignKey = @ForeignKey(name = "FK_ADDRESS_PROVINCE"))
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_MUNICIPALITY", foreignKey = @ForeignKey(name = "FK_ADDRESS_MUNICIPALITY"))
    private Municipality municipality;
}
