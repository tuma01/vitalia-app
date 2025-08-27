package com.amachi.app.vitalia.nurseprofessionspeciality.mapper;

import com.amachi.app.vitalia.common.mapper.AuditableIgnoreConfig;
import com.amachi.app.vitalia.common.mapper.BaseMapperConfig;
import com.amachi.app.vitalia.common.mapper.EntityDtoMapper;
import com.amachi.app.vitalia.nurseprofessionspeciality.dto.NurseProfessionSpecialityDto;
import com.amachi.app.vitalia.nurseprofessionspeciality.entity.NurseProfessionSpeciality;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
        config = BaseMapperConfig.class,
        builder = @Builder(disableBuilder = true)
)
public interface NurseProfessionSpecialityMapper extends EntityDtoMapper<NurseProfessionSpeciality, NurseProfessionSpecialityDto> {

    @Override
    @AuditableIgnoreConfig.IgnoreAuditableFields
    NurseProfessionSpeciality toEntity(NurseProfessionSpecialityDto dto);

    @Override
    NurseProfessionSpecialityDto toDto(NurseProfessionSpeciality entity);
}
