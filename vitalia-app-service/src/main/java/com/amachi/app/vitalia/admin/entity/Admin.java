package com.amachi.app.vitalia.admin.entity;

import com.amachi.app.vitalia.user.entity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "ADMIN")
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Admin extends Person {

    @Lob
    @Column(name = "PHOTO", columnDefinition = "mediumblob")
    private byte[] photo;
}
