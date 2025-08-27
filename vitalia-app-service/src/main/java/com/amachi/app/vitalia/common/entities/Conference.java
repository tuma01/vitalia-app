package com.amachi.app.vitalia.common.entities;

import com.amachi.app.vitalia.user.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "CONFERENCE")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOPIC", length = 100, nullable = false)
    private String topic;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "ORGANIZER", length = 100)
    private String organizer;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "IS_INTERNATIONAL", nullable = false)
    private boolean international;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_CONFERENCE_USERPROFILE"))
    private UserProfile profile;
}
