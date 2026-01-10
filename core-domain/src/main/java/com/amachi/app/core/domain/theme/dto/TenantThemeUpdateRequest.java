package com.amachi.app.core.domain.theme.dto;

import com.amachi.app.core.common.enums.ThemeMode;
import lombok.Data;

@Data
public class TenantThemeUpdateRequest {
    private String name;
    private String primaryColor;
    private String secondaryColor;
    private String accentColor;
    private String warnColor;
    private String backgroundColor;
    private String textColor;
    private String linkColor;
    private String buttonTextColor;
    private String fontFamily;
    private ThemeMode themeMode;
    private String propertiesJson;
}
