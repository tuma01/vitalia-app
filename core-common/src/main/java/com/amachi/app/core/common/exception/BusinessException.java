package com.amachi.app.core.common.exception;

/**
 * Excepción base para reglas de negocio (Capa de Dominio - Sin dependencias Web).
 * El manejo de códigos HTTP debe realizarse exclusivamente en la capa de infraestructura (boot).
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
