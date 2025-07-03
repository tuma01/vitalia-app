package com.amachi.app.vitalia.common.factory;

import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.common.utils.PersonType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PersonFactory {

    private final Map<PersonType, PersonCreator> creatorMap;

    public PersonFactory(List<PersonCreator> creators) {
        this.creatorMap = creators.stream()
                .collect(Collectors.toMap(PersonCreator::getSupportedType, Function.identity()));
    }

    public Person create(UserRegisterDto dto) {
        if (dto.getPersonType() == null) {
            throw new IllegalArgumentException("PersonType is required");
        }

        PersonCreator creator = creatorMap.get(dto.getPersonType());
        if (creator == null) {
            throw new IllegalArgumentException("No creator registered for: " + dto.getPersonType());
        }
        return creator.create(dto);
    }
}