-- ======================================================
-- Insertar Theme por defecto para la aplicación Vitalia
-- ======================================================

INSERT INTO DMN_THEME (
    ID,
    CODE,
    NAME,
    LOGO_URL,
    FAVICON_URL,
    PRIMARY_COLOR,
    SECONDARY_COLOR,
    BACKGROUND_COLOR,
    TEXT_COLOR,
    ACCENT_COLOR,
    WARN_COLOR,
    LINK_COLOR,
    BUTTON_TEXT_COLOR,
    FONT_FAMILY,
    THEME_MODE,
    PROPERTIES_JSON,
    CUSTOM_CSS,
    ALLOW_CUSTOM_CSS,
    ACTIVE,
    CREATED_BY,
    CREATED_DATE
)
VALUES (
    1,                           -- ID
    'DEFAULT_THEME',             -- Código único del Theme por defecto
    'Default Theme',             -- Nombre
    NULL,                        -- LOGO_URL (sin logo por defecto)
    NULL,                        -- FAVICON_URL (sin favicon por defecto)
    '#3f51b5',                   -- PRIMARY_COLOR
    '#ffffff',                   -- SECONDARY_COLOR
    '#f5f5f5',                   -- BACKGROUND_COLOR
    '#212121',                   -- TEXT_COLOR
    '#ff4081',                   -- ACCENT_COLOR
    '#f44336',                   -- WARN_COLOR
    '#3f51b5',                   -- LINK_COLOR
    '#ffffff',                   -- BUTTON_TEXT_COLOR
    'Roboto, sans-serif',        -- FONT_FAMILY
    'LIGHT',                      -- THEME_MODE
    '{}',                         -- PROPERTIES_JSON vacío por defecto
    NULL,                         -- CUSTOM_CSS vacío
    FALSE,                        -- ALLOW_CUSTOM_CSS por defecto
    TRUE,                         -- ACTIVE
    'SYSTEM',                     -- CREATED_BY
    CURRENT_TIMESTAMP                          -- CREATED_DATE
);