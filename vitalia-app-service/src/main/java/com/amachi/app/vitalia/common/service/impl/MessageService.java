package com.amachi.app.vitalia.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {
    private final MessageSource messageSource;

    @Autowired
    public MessageService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Obtiene un mensaje internacionalizado basado en una clave y argumentos.
     *
     * @param key  La clave del mensaje.
     * @param args Los argumentos para reemplazar en el mensaje.
     * @return El mensaje internacionalizado.
     */
    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, locale);
    }

    /**
     * Obtiene un mensaje internacionalizado basado en una clave, argumentos y un locale específico.
     *
     * @param key    La clave del mensaje.
     * @param args   Los argumentos para reemplazar en el mensaje.
     * @param locale El locale a utilizar.
     * @return El mensaje internacionalizado.
     */
    public String getMessage(String key, Object[] args, Locale locale) {
        return messageSource.getMessage(key, args, locale);
    }
}