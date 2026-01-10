package com.amachi.app.core.auth.bridge;


public interface AvatarBridge {

    /**
     * Crea un avatar por defecto para el usuario en el tenant indicado.
     */
    void createDefaultAvatar(Long userId, String tenantCode);

    /**
     * Actualiza (sube) el avatar del usuario.
     */
    void updateAvatar(Long userId, String tenantCode, org.springframework.web.multipart.MultipartFile file);

    /**
     * Elimina el avatar asociado al usuario (si existe).
     */
    void deleteAvatar(Long userId, String tenantCode);

    /**
     * Obtiene el avatar (bytes) del usuario; puede devolver null si no existe.
     */
    byte[] getAvatar(Long userId, String tenantCode);
}