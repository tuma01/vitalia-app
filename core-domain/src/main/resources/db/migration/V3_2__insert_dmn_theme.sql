-- ======================================================
-- Script: V3_2__insert_dmn_theme.sql
-- Descripción: Temas Maestros Genéricos (Marca Blanca)
-- ======================================================

INSERT INTO DMN_THEME (
    ID, CODE, NAME, LOGO_URL, FAVICON_URL, 
    PRIMARY_COLOR, SECONDARY_COLOR, BACKGROUND_COLOR, TEXT_COLOR, 
    ACCENT_COLOR, WARN_COLOR, LINK_COLOR, BUTTON_TEXT_COLOR, 
    FONT_FAMILY, THEME_MODE, PROPERTIES_JSON, CUSTOM_CSS, 
    ALLOW_CUSTOM_CSS, IS_TEMPLATE, ACTIVE, VERSION, CREATED_BY, CREATED_DATE
)
VALUES 
-- 1. Standard Light
(
    1, 'THEME_LIGHT_STD', 'Standard Light', NULL, NULL, 
    '#1976d2', '#ffffff', '#fafafa', '#212121', 
    '#ff4081', '#f44336', '#1976d2', '#ffffff', 
    'Roboto, sans-serif', 'LIGHT', '{}', NULL, 
    0, 1, 1, 0, 'SYSTEM', CURRENT_TIMESTAMP
),
-- 2. Modern Dark
(
    2, 'THEME_DARK_MOD', 'Modern Dark', NULL, NULL, 
    '#00bcd4', '#121212', '#121212', '#ffffff', 
    '#ff4081', '#f44336', '#00bcd4', '#ffffff', 
    'Roboto, sans-serif', 'DARK', '{}', NULL, 
    0, 1, 1, 0, 'SYSTEM', CURRENT_TIMESTAMP
),
-- 3. Emerald Soft
(
    3, 'THEME_GREEN_SOFT', 'Emerald Soft', NULL, NULL, 
    '#2e7d32', '#f1f8e9', '#f1f8e9', '#1b5e20', 
    '#ffa000', '#f44336', '#2e7d32', '#ffffff', 
    'Roboto, sans-serif', 'LIGHT', '{}', NULL, 
    0, 1, 1, 0, 'SYSTEM', CURRENT_TIMESTAMP
),
-- 4. Business Slate
(
    4, 'THEME_GRAY_BUS', 'Business Slate', NULL, NULL, 
    '#263238', '#eceff1', '#eceff1', '#263238', 
    '#b0bec5', '#f44336', '#263238', '#ffffff', 
    'Roboto, sans-serif', 'LIGHT', '{}', NULL, 
    0, 1, 1, 0, 'SYSTEM', CURRENT_TIMESTAMP
);