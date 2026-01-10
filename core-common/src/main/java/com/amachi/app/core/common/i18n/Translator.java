package com.amachi.app.core.common.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Translator {
    private static MessageSource messageSource;

    public Translator(MessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msgKey, Object[] args) {
        if (messageSource == null) {
            return msgKey; // fallback por si se llama muy temprano
        }
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgKey, args, msgKey, locale);
    }
}