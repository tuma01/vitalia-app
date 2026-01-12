package com.amachi.app.vitalia.clinical.avatar.dto.search;

import com.amachi.app.core.common.dto.BaseSearchDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvatarSearchDto implements BaseSearchDto {
    private Long id;
    private Long userId;
    private String tenantCode;
}
