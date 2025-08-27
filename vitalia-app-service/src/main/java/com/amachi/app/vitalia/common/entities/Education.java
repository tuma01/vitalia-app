package com.amachi.app.vitalia.common.entities;

import com.amachi.app.vitalia.user.entity.UserProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "EDUCATION")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INSTITUTION", nullable = false, length = 100)
    private String institution;

    @Column(name = "DEGREE", nullable = false, length = 100)
    private String degree;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ID_USERPROFILE", nullable = false, foreignKey = @ForeignKey(name = "FK_EDUCATION_USERPROFILE"))
    private UserProfile profile;
}
