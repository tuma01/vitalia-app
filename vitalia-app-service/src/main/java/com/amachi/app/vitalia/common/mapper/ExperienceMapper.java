package com.amachi.app.vitalia.common.mapper;

import com.amachi.app.vitalia.common.dto.ExperienceDto;
import com.amachi.app.vitalia.common.entities.Experience;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface ExperienceMapper extends EntityDtoMapper<Experience, ExperienceDto> {

    @Override
    @Mapping(target = "profile.id", source = "profileId")
    Experience toEntity(ExperienceDto dto);

    @Override
    @Mapping(target = "profileId", source = "profile.id")
    ExperienceDto toDto(Experience entity);
}
