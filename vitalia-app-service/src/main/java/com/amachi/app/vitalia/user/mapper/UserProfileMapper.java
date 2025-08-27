package com.amachi.app.vitalia.user.mapper;

import com.amachi.app.vitalia.common.mapper.*;
import com.amachi.app.vitalia.user.dto.UserProfileDto;
import com.amachi.app.vitalia.user.entity.UserProfile;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                EducationMapper.class,
                ExperienceMapper.class,
                CourseMapper.class,
                ConferenceMapper.class
        }
)
public interface UserProfileMapper extends EntityDtoMapper<UserProfile, UserProfileDto> {

    @Override
    UserProfile toEntity(UserProfileDto dto);

    @Override
    UserProfileDto toDto(UserProfile entity);
}
