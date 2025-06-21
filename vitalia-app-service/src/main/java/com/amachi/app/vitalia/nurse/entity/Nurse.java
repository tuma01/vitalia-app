package com.amachi.app.vitalia.nurse.entity;

import com.amachi.app.vitalia.entities.Person;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

import static com.amachi.app.vitalia.utils.AppConstants.USER_NURSE;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder//(toBuilder = true) // ,buildMethodName = "buildInternal"
@Entity
@Table(name = "NURSE")
@DiscriminatorValue(USER_NURSE)
@PrimaryKeyJoinColumn(name = "id")
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
