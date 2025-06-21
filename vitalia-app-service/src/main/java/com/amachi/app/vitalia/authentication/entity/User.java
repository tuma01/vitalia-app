package com.amachi.app.vitalia.authentication.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.entities.Person;
import com.amachi.app.vitalia.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

//@Builder
@Setter
@Getter
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@AllArgsConstructor//(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMAIL_USER", columnNames = "EMAIL")
})
@Builder
@ToString(exclude = "person")
public class User extends Auditable<String> implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Email {err.required}")
    @Email(message = "El email debe tener un formato válido")
    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)  // Asegúrate de tener Cascade si deseas persistir Admin automáticamente
    @JoinColumn(name = "FK_ID_PERSON", foreignKey = @ForeignKey(name = "FK_USER_PERSON"), referencedColumnName = "ID", nullable = false)
    private Person person;

    public void addRole(Role role) {
        roles.add(role);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Lob
    @Column(length = 1048576) // Definir tamaño máximo del BLOB (1MB en este caso)
    private byte[] avatar;
}
