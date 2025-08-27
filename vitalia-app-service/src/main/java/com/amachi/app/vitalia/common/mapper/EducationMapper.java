package com.amachi.app.vitalia.common.mapper;

import com.amachi.app.vitalia.common.dto.EducationDto;
import com.amachi.app.vitalia.common.entities.Education;
import com.amachi.app.vitalia.user.mapper.UserProfileMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                UserProfileMapper.class
        }
)
public interface EducationMapper extends EntityDtoMapper<Education, EducationDto> {

    @Override

    @Mapping(target = "profile.id", source = "profileId")
    Education toEntity(EducationDto dto);

    @Override
    @Mapping(target = "profileId", source = "profile.id")
    EducationDto toDto(Education entity);
}