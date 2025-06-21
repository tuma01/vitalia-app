package com.amachi.app.vitalia.entities;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.common.utils.PersonType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "ROLE")
@EqualsAndHashCode(callSuper=false)
public class Role extends Auditable<String> implements Model {

//    @NotEmpty(message = "Name of Role can not be a null or empty")
//    @Enumerated(EnumType.STRING)
//    @Column(name = "ROLE_NAME", unique = true, nullable = false)
//    @Builder.Default
//    private RoleEnum name = RoleEnum.USER;
//
//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private List<User> users;


    public Role(String name) {
//        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;



//    @Enumerated(EnumType.STRING)
//    private PersonType defaultPersonType; // Tipo de persona asociado (opcional)

}
