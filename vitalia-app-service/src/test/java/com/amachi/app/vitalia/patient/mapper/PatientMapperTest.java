package com.amachi.app.vitalia.patient.mapper;

import com.amachi.app.vitalia.address.mapper.AddressMapperImpl;
import com.amachi.app.vitalia.common.mapper.ConferenceMapper;
import com.amachi.app.vitalia.common.mapper.CourseMapper;
import com.amachi.app.vitalia.common.mapper.EducationMapper;
import com.amachi.app.vitalia.common.mapper.ExperienceMapper;
import com.amachi.app.vitalia.country.mapper.CountryMapper;
import com.amachi.app.vitalia.departamento.mapper.DepartamentoMapper;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoHospitalMapperImpl;
import com.amachi.app.vitalia.departamentothospital.mapper.DepartamentoTipoMapper;
import com.amachi.app.vitalia.historiamedica.mapper.HospitalizacionMapperImpl;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupport;
import com.amachi.app.vitalia.hospital.mapper.HospitalMapperSupportImpl;
import com.amachi.app.vitalia.nurse.mapper.NurseMapperImpl;
import com.amachi.app.vitalia.nurseprofessionspeciality.mapper.NurseProfessionSpecialityMapper;
import com.amachi.app.vitalia.patient.dto.PatientDto;
import com.amachi.app.vitalia.patient.entity.Patient;
import com.amachi.app.vitalia.role.mapper.RoleMapper;
import com.amachi.app.vitalia.room.mapper.RoomMapperImpl;
import com.amachi.app.vitalia.user.mapper.UserMapperImpl;
import com.amachi.app.vitalia.user.mapper.UserProfileMapperImpl;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PatientMapperTest {
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        patientMapper = new PatientMapperImpl(
                Mappers.getMapper(CountryMapper.class),
                new RoomMapperImpl(
                        new HospitalizacionMapperImpl(
                                new DepartamentoHospitalMapperImpl(
                                        Mappers.getMapper(DepartamentoTipoMapper.class))
                                )
                        ),
                new InsuranceMapperImpl(
                        new AddressMapperImpl(
                                Mappers.getMapper(CountryMapper.class),
                                Mappers.getMapper(DepartamentoMapper.class)
                        )
                ),
                new NurseMapperImpl(
                        Mappers.getMapper(NurseProfessionSpecialityMapper.class),
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
                        new HospitalMapperSupportImpl()
                ),

                Mappers.getMapper(HabitoToxicoMapper.class),
                Mappers.getMapper(HabitoFisiologicoMapper.class),
                Mappers.getMapper(EmergencyContactMapper.class),

                new AddressMapperImpl(
                        Mappers.getMapper(CountryMapper.class),
                        Mappers.getMapper(DepartamentoMapper.class)
                ),
                new UserMapperImpl(Mappers.getMapper(RoleMapper.class)),
                Mappers.getMapper(HospitalMapperSupport.class)
        );
    }

    @Test
    void shouldMapPatientDtoToEntity() {
        var dto = Instancio.create(PatientDto.class);
        var entity = patientMapper.toEntity(dto);
        // Add assertions to verify the mapping
        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getIdCard(), entity.getIdCard());
        assertEquals(dto.getPatientStatus(), entity.getPatientStatus());
        assertEquals(dto.getOccupation(), entity.getOccupation());
        assertEquals(dto.getDegreeOfInstruction(), entity.getDegreeOfInstruction());
        assertNotNull(entity.getCountryOfBirth());
        assertEquals(dto.getCountryOfBirth().getId(), entity.getCountryOfBirth().getId());
        assertEquals(dto.getCountryOfBirth().getName(), entity.getCountryOfBirth().getName());
        assertEquals(dto.getCountryOfBirth().getIso(), entity.getCountryOfBirth().getIso());
        assertNotNull(entity.getRoom());
        assertEquals(dto.getRoom().getId(), entity.getRoom().getId());
        assertEquals(dto.getRoom().getBlockCode(), entity.getRoom().getBlockCode());
        assertNotNull(entity.getNurse());
        assertEquals(dto.getNurse().getId(), entity.getNurse().getId());
        assertEquals(dto.getNurse().getIdCard(), entity.getNurse().getIdCard());
        assertEquals(dto.getNurse().getApellidoMaterno(), entity.getNurse().getApellidoMaterno());
        assertEquals(dto.getNurse().getNombre(), entity.getNurse().getNombre());
        assertEquals(dto.getAdditionalRemarks(), entity.getAdditionalRemarks());
        assertNotNull(entity.getAddress());
        assertEquals(dto.getAddress().getId(), entity.getAddress().getId());
        assertEquals(dto.getAddress().getDireccion(), entity.getAddress().getDireccion());
        assertEquals(dto.getAddress().getCiudad(), entity.getAddress().getCiudad());
        assertNotNull(entity.getHabitoToxico());
        assertEquals(dto.getHabitoToxico().getAlcohol(), entity.getHabitoToxico().getAlcohol());
        assertNotNull(entity.getHabitoFisiologico());
        assertEquals(dto.getHabitoFisiologico().getActividadesDeportivas(), entity.getHabitoFisiologico().getActividadesDeportivas());
        assertNotNull(entity.getEmergencyContact());
        assertEquals(dto.getEmergencyContact().getName(), entity.getEmergencyContact().getName());
        assertNotNull(entity.getUser());
        assertEquals(dto.getUser().getId(), entity.getUser().getId());
        assertEquals(dto.getHospitalizacionesIds().size(), entity.getHospitalizaciones().size());
    }

    @Test
    void shouldMapPatientEntityToDto() {
        var entity = Instancio.create(Patient.class);
        var dto = patientMapper.toDto(entity);
        // Add assertions to verify the mapping
        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getIdCard(), dto.getIdCard());
        assertEquals(entity.getPatientStatus(), dto.getPatientStatus());
        assertEquals(entity.getOccupation(), dto.getOccupation());
        assertEquals(entity.getDegreeOfInstruction(), dto.getDegreeOfInstruction());
        assertNotNull(dto.getCountryOfBirth());
        assertEquals(entity.getCountryOfBirth().getId(), dto.getCountryOfBirth().getId());
        assertEquals(entity.getCountryOfBirth().getName(), dto.getCountryOfBirth().getName());
        assertEquals(entity.getCountryOfBirth().getIso(), dto.getCountryOfBirth().getIso());
        assertNotNull(dto.getRoom());
        assertEquals(entity.getRoom().getId(), dto.getRoom().getId());
        assertEquals(entity.getRoom().getBlockCode(), dto.getRoom().getBlockCode());
        assertNotNull(dto.getNurse());
        assertEquals(entity.getNurse().getId(), dto.getNurse().getId());
        assertEquals(entity.getNurse().getIdCard(), dto.getNurse().getIdCard());
        assertEquals(entity.getNurse().getApellidoMaterno(), dto.getNurse().getApellidoMaterno());
        assertEquals(entity.getNurse().getNombre(), dto.getNurse().getNombre());
        assertEquals(entity.getAdditionalRemarks(), dto.getAdditionalRemarks());
        assertNotNull(dto.getAddress());
        assertEquals(entity.getAddress().getId(), dto.getAddress().getId());
        assertEquals(entity.getAddress().getDireccion(), dto.getAddress().getDireccion());
        assertEquals(entity.getAddress().getCiudad(), dto.getAddress().getCiudad());
        assertNotNull(dto.getHabitoToxico());
        assertEquals(entity.getHabitoToxico().getAlcohol(), dto.getHabitoToxico().getAlcohol());
        assertNotNull(dto.getHabitoFisiologico());
        assertEquals(entity.getHabitoFisiologico().getActividadesDeportivas(), dto.getHabitoFisiologico().getActividadesDeportivas());
        assertNotNull(dto.getEmergencyContact());
        assertEquals(entity.getEmergencyContact().getName(), dto.getEmergencyContact().getName());
        assertNotNull(dto.getUser());
        assertEquals(entity.getUser().getId(), dto.getUser().getId());
        assertEquals(entity.getHospitalizaciones().size(), dto.getHospitalizacionesIds().size());

    }
}
