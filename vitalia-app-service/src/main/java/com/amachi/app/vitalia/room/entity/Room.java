package com.amachi.app.vitalia.room.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.common.utils.TypeRoomEnum;
import com.amachi.app.vitalia.historiamedica.entity.Hospitalizacion;
import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.patient.entity.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "ROOM")
public class Room implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMBER_ROOM")
    private Integer numberRoom;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "PRIVATE_ROOM")
    @Builder.Default
    private Boolean privateRoom = false;

    @Column(name = "TYPE_ROOM")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TypeRoomEnum typeRoom = TypeRoomEnum.ESTANDAR;

    @Column(name = "NUMBER_OF_BEDS")
    private Integer numberOfBeds;

    @Column(name = "BLOCK_FLOOR")
    private Integer blockFloor;

    @NotNull(message = "blockCode shouldn't be null")
    @NotBlank(message = "blockCode shouldn't be blank")
    @Column(name = "BLOCK_CODE", nullable = false, length = 10)
    private String blockCode;

    @Column(name = "UNAVAILABLE")
    private Boolean unavailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_HOSPITAL")
    private Hospital hospital;

    @OneToMany(mappedBy = "room")
    private Set<Patient> patients;

    @OneToMany(mappedBy = "room")
    private Set<Hospitalizacion> hospitalizaciones;
}
