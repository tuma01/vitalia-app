package com.amachi.app.vitalia.common.mapper;

import com.amachi.app.vitalia.common.dto.CourseDto;
import com.amachi.app.vitalia.common.entities.Course;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface CourseMapper extends EntityDtoMapper<Course, CourseDto> {

    @Override
    @Mapping(target = "profile.id", source = "profileId")
    Course toEntity(CourseDto dto);

    @Override
    @Mapping(target = "profileId", source = "profile.id")
    CourseDto toDto(Course entity);
}
