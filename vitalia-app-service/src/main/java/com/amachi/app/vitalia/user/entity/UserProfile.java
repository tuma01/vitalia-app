package com.amachi.app.vitalia.user.entity;

import com.amachi.app.vitalia.common.entities.Conference;
import com.amachi.app.vitalia.common.entities.Course;
import com.amachi.app.vitalia.common.entities.Education;
import com.amachi.app.vitalia.common.entities.Experience;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "USER_PROFILE")
public class UserProfile { //abstract
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String biography;

    @Lob
    @Column(name = "PHOTO")
    private byte[] photo;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educationList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experienceList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courseList = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conference> conferenceList = new ArrayList<>();
}
