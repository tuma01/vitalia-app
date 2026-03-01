package com.amachi.app.vitalia.medicalcatalog.allergy.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.allergy.dto.AllergyDto;
import com.amachi.app.vitalia.medicalcatalog.allergy.entity.Allergy;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface AllergyMapper extends EntityDtoMapper<Allergy, AllergyDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    Allergy toEntity(AllergyDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AllergyDto dto, @MappingTarget Allergy entity);

    @Override
    @BeanMapping(unmappedSourcePolicy = ReportingPolicy.IGNORE)
    AllergyDto toDto(Allergy entity);
}
