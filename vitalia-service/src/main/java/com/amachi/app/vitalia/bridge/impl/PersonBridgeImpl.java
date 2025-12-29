package com.amachi.app.vitalia.bridge.impl;

import com.amachi.app.vitalia.authentication.bridge.PersonBridge;
import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.common.dto.UserSummaryDto;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.factory.PersonFactory;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonBridgeImpl implements PersonBridge {

    private final PersonFactory personFactory;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Long createPerson(UserRegisterRequest request) {
        log.info("🧩 Creando persona para registro de usuario [{}]...", request.getEmail());

        Person person = personFactory.create(request);
        person.setCreatedBy("SYSTEM");
        person.setCreatedDate(LocalDateTime.now());
        personRepository.save(person);

        log.info("✅ Persona creada con ID [{}] para usuario [{}]", person.getId(), request.getEmail());
        return person.getId();
    }

    @Override
    public UserSummaryDto buildUserSummary(User user, Tenant tenant) {

        // buscar person del user usando personId, no userId
        Person person = null;
        if (user.getPersonId() != null) {
            person = personRepository.findById(user.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException(Person.class.getName(), "error.resource.not.found",
                            user.getPersonId()));
        }

        return UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .tenantName(tenant.getName()) // Populate tenantName
                .roles(
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .enabled(user.isEnabled())
                .personName(person != null ? buildFullName(person) : null)
                .personType(person != null ? person.getPersonType() : null)
                .build();
    }

    private String buildFullName(Person person) {
        return String.join(" ",
                person.getNombre(),
                person.getSegundoNombre() != null ? person.getSegundoNombre() : "",
                person.getApellidoPaterno(),
                person.getApellidoMaterno() != null ? person.getApellidoMaterno() : "").trim();
    }
}
