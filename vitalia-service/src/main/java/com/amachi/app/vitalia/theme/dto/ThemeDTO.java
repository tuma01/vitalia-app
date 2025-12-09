package com.amachi.app.vitalia.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.amachi.app.vitalia.common.enums.ThemeMode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDTO {
    private Long id;
    private String name;
    private String logoUrl;
    private String faviconUrl;
    private String primaryColor;
    private String accentColor;
    private String warnColor;
    private String secondaryColor;
    private String backgroundColor;
    private String textColor;
    private String linkColor;
    private String buttonTextColor;
    private String fontFamily;
    private ThemeMode themeMode;
    private String propertiesJson;
}
