package com.amachi.app.vitalia.common.entities;

import com.amachi.app.vitalia.user.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "COURSE")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @Column(name = "INSTITUTION", nullable = false, length = 100)
    private String institution;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "DURATION_IN_HOURS", nullable = false)
    private int durationInHours;

    @Column(name = "CERTIFICATE")
    private String certificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_COURSE_USERPROFILE"))
    private UserProfile profile;
}