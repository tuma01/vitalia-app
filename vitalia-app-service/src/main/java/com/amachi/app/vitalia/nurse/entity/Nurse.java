package com.amachi.app.vitalia.nurse.entity;

import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Table(name = "NURSE")
@DiscriminatorValue("NURSE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Nurse extends Person {

    @Lob
    @Column(name = "PHOTO", columnDefinition = "mediumblob")
    private byte[] photo;

    @Column(name = "ID_CARD", unique = true, length = 100)
    private String idCard;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "NURSE_NURSEPROFESSIONSPECIALITY",
            joinColumns = @JoinColumn(name = "ID_NURSE", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_NURSE_PROFESSION_SPECIALITY", referencedColumnName = "ID"))
    private Set<NurseProfessionSpeciality> nurseProfessionSpecialities;
}
