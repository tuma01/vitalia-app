package com.amachi.app.vitalia.departamentothospital.mapper;

import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.departamentothospital.dto.DepartamentoHospitalDto;
import com.amachi.app.vitalia.departamentothospital.entity.DepartamentoHospital;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true),
        uses = {
                DepartamentoTipoMapper.class,
        }
)
public interface DepartamentoHospitalMapper extends EntityDtoMapper<DepartamentoHospital, DepartamentoHospitalDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    @Mapping(target = "hospital.id", source = "hospitalId")
    @Mapping(target = "headDoctor.id", source = "headDoctorId")
    DepartamentoHospital toEntity(DepartamentoHospitalDto dto);

    @Override
    @Mapping(target = "hospitalId", source = "hospital.id")
    @Mapping(target = "headDoctorId", source = "headDoctor.id")
    DepartamentoHospitalDto toDto(DepartamentoHospital entity);
}