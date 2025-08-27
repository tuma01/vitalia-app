package com.amachi.app.vitalia.doctor.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapperImpl;
import com.amachi.app.vitalia.common.mapper.ConferenceMapper;
import com.amachi.app.vitalia.common.mapper.CourseMapper;
import com.amachi.app.vitalia.common.mapper.EducationMapper;
import com.amachi.app.vitalia.common.mapper.ExperienceMapper;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.doctor.dto.DoctorDto;
import com.amachi.app.vitalia.doctor.entity.Doctor;
import com.amachi.app.vitalia.doctorprofessionspeciality.mapper.DoctorProfessionSpecialityMapper;
import com.amachi.app.vitalia.historiamedica.mapper.*;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupport;
import com.amachi.app.vitalia.role.mapper.RoleMapper;
import com.amachi.app.vitalia.user.mapper.UserMapperImpl;
import com.amachi.app.vitalia.user.mapper.UserProfileMapperImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DoctorMapperTest {

    private DoctorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DoctorMapperImpl(
                Mappers.getMapper(DoctorProfessionSpecialityMapper.class),
                Mappers.getMapper(DoctorHospitalAssignmentMapper.class),
                new HistoriaMedicaMapperImpl(
                        new ConsultaMedicaMapperImpl(Mappers.getMapper(TipoConsultaMapper.class)),
                        Mappers.getMapper(EnfermedadActualMapper.class),
                        Mappers.getMapper(RegistroEnfermedadMapper.class)
                ),
                new UserProfileMapperImpl(
                        Mappers.getMapper(EducationMapper.class),
                        Mappers.getMapper(ExperienceMapper.class),
                        Mappers.getMapper(CourseMapper.class),
                        Mappers.getMapper(ConferenceMapper.class)
                ),
                new AddressMapperImpl(
                        Mappers.getMapper(CountryMapper.class),
                        Mappers.getMapper(DepartamentoMapper.class)
                ),
                new UserMapperImpl(Mappers.getMapper(RoleMapper.class)),
                Mappers.getMapper(HospitalMapperSupport.class)
        );
    }

    @Test
    void shouldMapDoctorDtoToEntity() {

        DoctorDto dto = Instancio.of(DoctorDto.class)
                .set(field(DoctorDto::getIsAvailable), true)
                .create();
        Doctor doctor = mapper.toEntity(dto);

        assertThat(doctor).isNotNull();
        assertThat(doctor.getLicenseNumber()).isEqualTo(dto.getLicenseNumber());
        assertThat(doctor.getIsAvailable()).isTrue();
        assertThat(doctor.getSpecialtiesSummary()).isEqualTo(dto.getSpecialtiesSummary());
        assertThat(doctor.getYearsOfExperience()).isEqualTo(dto.getYearsOfExperience());
        assertThat(doctor.getRating()).isEqualTo(dto.getRating());
        assertEquals(doctor.getHistoriaMedicas().size(), dto.getHistoriaMedicas().size());
        assertEquals(doctor.getDoctorProfessionSpecialities().size(), dto.getDoctorProfessionSpecialities().size());
        assertEquals(doctor.getDoctorHospitalAssignments().size(), dto.getDoctorHospitalAssignments().size());
    }

    @Test
    void shouldMapDoctorEntityToDto() {
        Doctor doctor = Instancio.of(Doctor.class)
                .set(field(Doctor::getIsAvailable), true)
                .create();
        DoctorDto dto = mapper.toDto(doctor);
        assertThat(dto).isNotNull();
        assertThat(dto.getLicenseNumber()).isEqualTo(doctor.getLicenseNumber());
        assertThat(dto.getIsAvailable()).isTrue();
        assertThat(dto.getSpecialtiesSummary()).isEqualTo(doctor.getSpecialtiesSummary());
        assertThat(dto.getYearsOfExperience()).isEqualTo(doctor.getYearsOfExperience());
        assertThat(dto.getRating()).isEqualTo(doctor.getRating());
        assertEquals(dto.getHistoriaMedicas().size(), doctor.getHistoriaMedicas().size());
        assertEquals(dto.getDoctorProfessionSpecialities().size(), doctor.getDoctorProfessionSpecialities().size());
        assertEquals(dto.getDoctorHospitalAssignments().size(), doctor.getDoctorHospitalAssignments().size());
    }
}