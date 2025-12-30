package com.amachi.app.vitalia.person.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "TEST_PERSON")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TestPerson extends Person {
    // No extra fields needed, just a concrete implementation
}
