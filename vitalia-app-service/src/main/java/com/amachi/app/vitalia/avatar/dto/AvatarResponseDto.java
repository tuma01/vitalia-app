package com.amachi.app.vitalia.avatar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvatarResponseDto {
    private byte[] content;
    private String mimeType;
    private String filename;
    private Long userId;
}
