package com.amachi.app.vitalia.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    @PostConstruct
    public void init() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("errors"); // Asegúrate de que el archivo errors.properties esté en el classpath
    }

    public static String toLocale(String code, Object[] args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }
}
