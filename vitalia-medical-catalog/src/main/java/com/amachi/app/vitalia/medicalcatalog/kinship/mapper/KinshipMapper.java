package com.amachi.app.vitalia.medicalcatalog.kinship.mapper;

import com.amachi.app.core.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.core.common.mapper.BaseMapperConfig;
import com.amachi.app.core.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.medicalcatalog.kinship.dto.KinshipDto;
import com.amachi.app.vitalia.medicalcatalog.kinship.entity.Kinship;
import org.mapstruct.*;

@Mapper(config = BaseMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface KinshipMapper extends EntityDtoMapper<Kinship, KinshipDto> {
    @Override @AuditableIgnoreConfig.IgnoreAuditableFields
    Kinship toEntity(KinshipDto dto);

    @AuditableIgnoreConfig.IgnoreAuditableFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(KinshipDto dto, @MappingTarget Kinship entity);

    @Override @BeanMapping(unmappedSourcePolicy = org.mapstruct.ReportingPolicy.IGNORE)
    KinshipDto toDto(Kinship entity);
}
