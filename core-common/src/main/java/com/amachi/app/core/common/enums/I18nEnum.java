package com.amachi.app.core.common.enums;

public interface I18nEnum {
    default String getMessageKey(Enum<?> e) {
        return e.getClass().getSimpleName() + "." + e.name().toLowerCase();
    }
}
