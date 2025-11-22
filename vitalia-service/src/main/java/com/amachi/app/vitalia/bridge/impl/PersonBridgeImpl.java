package com.amachi.app.vitalia.bridge.impl;

import com.amachi.app.vitalia.authentication.bridge.PersonBridge;
import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.person.entity.Person;
import com.amachi.app.vitalia.person.factory.PersonFactory;
import com.amachi.app.vitalia.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
