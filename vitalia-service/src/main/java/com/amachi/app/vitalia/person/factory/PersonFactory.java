package com.amachi.app.vitalia.person.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.common.enums.PersonType;
import com.amachi.app.vitalia.person.creator.PersonCreator;
import com.amachi.app.vitalia.person.entity.Person;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
//@RequiredArgsConstructor
//@AllArgsConstructor
public class PersonFactory {

    private final Map<PersonType, PersonCreator> creatorMap;

    public PersonFactory(List<PersonCreator> creators) {
        this.creatorMap = creators.stream()
                .collect(Collectors.toMap(PersonCreator::getSupportedType, Function.identity()));
    }


    /**
     * Crea una persona usando datos de RegisterRequest desde authentication bridge
     */
    public Person create(UserRegisterRequest dto) {
        if (dto.getPersonType() == null) {
            throw new IllegalArgumentException("PersonType is required");
        }
        return createByType(dto.getPersonType(), dto);
    }

    /**
     * Crear persona por tipo (bootstrap o defaults)
     */
    public Person create(PersonType type) {
        return createByType(type, null);
    }

    private Person createByType(PersonType type, UserRegisterRequest dto) {
        PersonCreator creator = creatorMap.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("No creator registered for: " + type);
        }
        return creator.create(dto);
    }
}
