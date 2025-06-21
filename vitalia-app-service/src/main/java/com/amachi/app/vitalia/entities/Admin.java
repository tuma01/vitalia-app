package com.amachi.app.vitalia.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.amachi.app.vitalia.utils.AppConstants.USER_ADMIN;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder //genera un constructor con todos los parámetros
@Entity
@Table(name = "ADMIN")
@DiscriminatorValue(USER_ADMIN)
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Person {

    @Lob
    @Column(name = "PHOTO", columnDefinition = "mediumblob")
    private byte[] photo;
}
