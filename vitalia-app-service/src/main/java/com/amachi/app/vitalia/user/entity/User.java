package com.amachi.app.vitalia.user.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMAIL_USER", columnNames = "EMAIL")
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(exclude = "person")
public class User extends Auditable<String> implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank(message = "Email {err.required}")
    @Email(message = "El email debe tener un formato válido")
    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @NotBlank(message = "Password {err.required}")
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_LOCKED", nullable = false)
    private boolean accountLocked;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @Lob
    @Column(name = "AVATAR", length = 1048576) // Definir tamaño máximo del BLOB (1MB en este caso)
    private byte[] avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "FK_ID_PERSON", foreignKey = @ForeignKey(name = "FK_USER_PERSON"), referencedColumnName = "ID")
    private Person person;

    // Métodos de seguridad
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return !this.accountLocked; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return this.enabled; }

    @Override
    public String getName() {
        return this.email;
    }

    public void addRole(Role role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }
}
