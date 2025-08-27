package com.amachi.app.vitalia.departamentothospital.entity;

import com.amachi.app.vitalia.common.entities.Auditable;
import com.amachi.app.vitalia.common.entities.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "DEPARTAMENTO_TIPO")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class DepartamentoTipo extends Auditable<String> implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name of Department of Hospital shouldn't be null")
    @Column(name = "NAME", nullable = false, unique = true)
    private String name; // Ej: "Pediatría", "Neurología"

    @Column(name = "DESCRIPTION")
    private String description;
}
