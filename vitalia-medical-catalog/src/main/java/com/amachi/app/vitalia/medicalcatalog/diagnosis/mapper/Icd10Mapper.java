package com.amachi.app.vitalia.medicalcatalog.diagnosis.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.dto.Icd10Dto;
import com.amachi.app.vitalia.medicalcatalog.diagnosis.entity.Icd10;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface Icd10Mapper extends EntityDtoMapper<Icd10, Icd10Dto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Icd10 toEntity(Icd10Dto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(Icd10Dto dto, @MappingTarget Icd10 entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    Icd10Dto toDto(Icd10 entity);
}
