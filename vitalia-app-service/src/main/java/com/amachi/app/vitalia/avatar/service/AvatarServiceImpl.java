package com.amachi.app.vitalia.avatar.service;

import com.amachi.app.vitalia.avatar.config.AvatarProperties;
import com.amachi.app.vitalia.avatar.exception.InvalidAvatarException;
import com.amachi.app.vitalia.user.entity.User;
import com.amachi.app.vitalia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService {

    private final UserRepository userRepository;
    private final AvatarProperties avatarProperties;
    private final ResourceLoader resourceLoader;

    @Override
    public void updateAvatar(Long userId, MultipartFile file) {
        validateAvatarFile(file);

        byte[] avatarBytes = processImage(file);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));

        user.setAvatar(avatarBytes);
        userRepository.save(user);

        log.info("Avatar actualizado para el usuario con ID: {}", userId);
    }

    @Override
    public byte[] getAvatar(Long userId) {
        return userRepository.findById(userId)
                .map(User::getAvatar)
                .filter(Objects::nonNull)
                .orElseGet(this::getDefaultAvatar);
    }

    @Override
    public byte[] getAvatarByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getAvatar)
                .filter(Objects::nonNull)
                .orElseGet(this::getDefaultAvatar);
    }

    @Override
    public void deleteAvatar(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
        user.setAvatar(null);
        userRepository.save(user);
        log.info("Avatar eliminado para el usuario con ID: {}", userId);
    }

    @Override
    public byte[] getDefaultAvatar() {
        try {
            Resource resource = resourceLoader.getResource(avatarProperties.getDefaultPath());
            return StreamUtils.copyToByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.error("No se pudo cargar el avatar por defecto", e);
            return new byte[0]; // Evita errores de frontend, aunque preferible lanzar excepción si es crítico
        }
    }

    private void validateAvatarFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidAvatarException("El archivo de avatar está vacío.");
        }

        String mimeType = file.getContentType();
        if (mimeType == null || !avatarProperties.getAllowedTypes().contains(mimeType)) {
            throw new InvalidAvatarException("Tipo de archivo no permitido: " + mimeType);
        }

        if (file.getSize() > avatarProperties.getMaxSize()) {
            throw new InvalidAvatarException("El archivo de avatar supera el tamaño máximo permitido.");
        }
    }

    private byte[] processImage(MultipartFile file) {
        try {
            return file.getBytes(); // Podrías integrar Thumbnailator para resize
        } catch (IOException e) {
            throw new InvalidAvatarException("No se pudo procesar la imagen", e);
        }
    }
}
