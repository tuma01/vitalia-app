package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entities.Model;
import com.amachi.app.vitalia.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Token implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(name = "FK_ID_USER", nullable = false)
    private User user;
}
