package com.amachi.app.vitalia.medicalcatalog.vaccine.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.vaccine.dto.VaccineDto;
import com.amachi.app.vitalia.medicalcatalog.vaccine.entity.Vaccine;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface VaccineMapper extends EntityDtoMapper<Vaccine, VaccineDto> {
    @Override @AuditableIgnoreConfig.IgnoreAuditableFields
    Vaccine toEntity(VaccineDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(VaccineDto dto, @MappingTarget Vaccine entity);

    @Override @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    VaccineDto toDto(Vaccine entity);
}
