package com.amachi.app.vitalia.medicalcatalog.demographic.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.demographic.dto.CivilStatusDto;
import com.amachi.app.vitalia.medicalcatalog.demographic.entity.CivilStatus;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface CivilStatusMapper extends EntityDtoMapper<CivilStatus, CivilStatusDto> {
    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    CivilStatus toEntity(CivilStatusDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CivilStatusDto dto, @MappingTarget CivilStatus entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    CivilStatusDto toDto(CivilStatus entity);
}
