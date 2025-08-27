package com.amachi.app.vitalia.user.mapper;

import com.amachi.app.vitalia.common.mapper.*;
import com.amachi.app.vitalia.user.dto.UserProfileDto;
import com.amachi.app.vitalia.user.entity.UserProfile;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserProfileMapperTest {

    private UserProfileMapper userProfileMapper;

    @BeforeEach
    void setUp() {
        userProfileMapper = new UserProfileMapperImpl(
                Mappers.getMapper(EducationMapper.class),
                Mappers.getMapper(ExperienceMapper.class),
                Mappers.getMapper(CourseMapper.class),
                Mappers.getMapper(ConferenceMapper.class)
        );
    }

    @Test
    void shouldMapUserProfileDtoToEntity() {
        UserProfileDto dto = Instancio.create(UserProfileDto.class);

        var entity = userProfileMapper.toEntity(dto);
        assertNotNull(entity);
        assertEquals(dto.getBiography(), entity.getBiography());

        assertEquals(dto.getConferenceList().size(), entity.getConferenceList().size());
        assertEquals(dto.getConferenceList().getFirst().getOrganizer(), entity.getConferenceList().getFirst().getOrganizer());

        assertEquals(dto.getCourseList().size(), entity.getCourseList().size());
        assertEquals(dto.getCourseList().getFirst().getInstitution(), entity.getCourseList().getFirst().getInstitution());
        assertEquals(dto.getCourseList().getFirst().getCertificate(), entity.getCourseList().getFirst().getCertificate());

        assertEquals(dto.getExperienceList().size(), entity.getExperienceList().size());
        assertEquals(dto.getExperienceList().getFirst().getInstitution(), entity.getExperienceList().getFirst().getInstitution());

        assertEquals(dto.getEducationList().size(), entity.getEducationList().size());
        assertEquals(dto.getEducationList().getFirst().getDegree(), entity.getEducationList().getFirst().getDegree());
    }

    @Test
    void shouldMapUserProfileToDto() {
        var entity = Instancio.create(UserProfile.class);

        var dto = userProfileMapper.toDto(entity);
        assertNotNull(dto);
        assertEquals(entity.getBiography(), dto.getBiography());

        assertEquals(entity.getConferenceList().size(), dto.getConferenceList().size());
        assertEquals(entity.getConferenceList().getFirst().getOrganizer(), dto.getConferenceList().getFirst().getOrganizer());

        assertEquals(entity.getCourseList().size(), dto.getCourseList().size());
        assertEquals(entity.getCourseList().getFirst().getInstitution(), dto.getCourseList().getFirst().getInstitution());
        assertEquals(entity.getCourseList().getFirst().getCertificate(), dto.getCourseList().getFirst().getCertificate());

        assertEquals(entity.getExperienceList().size(), dto.getExperienceList().size());
        assertEquals(entity.getExperienceList().getFirst().getInstitution(), dto.getExperienceList().getFirst().getInstitution());

        assertEquals(entity.getEducationList().size(), dto.getEducationList().size());
        assertEquals(entity.getEducationList().getFirst().getDegree(), dto.getEducationList().getFirst().getDegree());
    }
}