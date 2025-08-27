package com.amachi.app.vitalia.common.mapper;

import com.amachi.app.vitalia.common.dto.ConferenceDto;
import com.amachi.app.vitalia.common.entities.Conference;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface ConferenceMapper extends EntityDtoMapper<Conference, ConferenceDto> {

    @Override
    @Mapping(target = "profile.id", source = "profileId")
    Conference toEntity(ConferenceDto dto);

    @Override
    @Mapping(target = "profileId", source = "profile.id")
    ConferenceDto toDto(Conference entity);
}
