package com.amachi.app.vitalia.common.dto;


import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Hidden
public class PageResponseDto<T> {
    private List<T> content;
    private long totalElements;
    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;
    private int numberOfElements;
}