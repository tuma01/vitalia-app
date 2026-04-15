package com.amachi.app.core.auth.entity;

import com.amachi.app.core.common.entity.BaseTenantEntity;
import com.amachi.app.core.domain.entity.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad principal que representa la cuenta del usuario en el sistema (Elite Tier).
 * (Manual Implementation to resolve Lombok resolution bottlenecks during refactoring)
 */
@Entity
@Table(name = "AUT_USER")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class User extends BaseTenantEntity implements UserDetails, Principal {

    @NotBlank(message = "Email {err.required}")
    @Email(message = "El email debe tener un formato válido")
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "Password {err.required}")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_LOCKED", nullable = false)
    private boolean accountLocked;

    @Builder.Default
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled = true;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UserAccount> userAccounts = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void normalizeUser() {
        if (this.email != null) {
            this.email = this.email.toLowerCase().trim();
        }
        if (getTenantId() == null) {
            setTenantId("SYSTEM");
        }
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() { return this.email; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return !this.accountLocked; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public String getName() { return this.email; }

    public Long getPersonId() {
        return person != null ? person.getId() : null;
    }
}
