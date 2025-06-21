package com.amachi.app.vitalia.authentication.dto.search;

import com.amachi.app.vitalia.common.dto.search.BaseSearchDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Hidden
public class UserSearchDto implements BaseSearchDto {
    private Long id;
    private String username;
    private String email;

    @Override
    public Long getId() {
        return id;
    }
}
