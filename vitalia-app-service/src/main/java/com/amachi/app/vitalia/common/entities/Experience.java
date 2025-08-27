package com.amachi.app.vitalia.common.entities;

import com.amachi.app.vitalia.user.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "EXPERIENCE")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @Column(name = "INSTITUTION", nullable = false, length = 100)
    private String institution;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_EXPERIENCE_USERPROFILE"))
    private UserProfile profile;
}