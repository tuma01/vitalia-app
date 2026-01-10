package com.amachi.app.vitalia.medicalcatalog.procedure.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.procedure.dto.MedicalProcedureDto;
import com.amachi.app.vitalia.medicalcatalog.procedure.entity.MedicalProcedure;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface MedicalProcedureMapper extends EntityDtoMapper<MedicalProcedure, MedicalProcedureDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    MedicalProcedure toEntity(MedicalProcedureDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(MedicalProcedureDto dto, @MappingTarget MedicalProcedure entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    MedicalProcedureDto toDto(MedicalProcedure entity);
}
