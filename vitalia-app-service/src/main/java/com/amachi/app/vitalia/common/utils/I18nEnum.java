package com.amachi.app.vitalia.common.utils;

public interface I18nEnum {
    default String getMessageKey(Enum<?> e) {
        return e.getClass().getSimpleName() + "." + e.name().toLowerCase();
    }
}
