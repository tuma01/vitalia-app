package com.amachi.app.vitalia.avatar.service;

import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.repository.TenantRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.avatar.config.AvatarProperties;
import com.amachi.app.vitalia.avatar.entity.Avatar;
import com.amachi.app.vitalia.avatar.exception.InvalidAvatarException;
import com.amachi.app.vitalia.avatar.repository.AvatarRepository;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService {

    private final AvatarRepository avatarRepository;
    private final AvatarProperties avatarProperties;
    private final ResourceLoader resourceLoader;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    @Override
    public Avatar saveAvatar(MultipartFile file, Long userId, String tenantCode) {
        validateAvatarFile(file);
        // Validar existencia del tenant
        Tenant tenant = tenantRepository.findByCode(tenantCode)
                .orElseThrow(() -> new ResourceNotFoundException(Tenant.class.getName(), "error.resource.not.found", tenantCode));
//                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.tenant.not_found", tenantCode));
        // Validar existencia del user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class.getName(), "error.resource.not.found", userId));

        byte[] content = processFile(file);

        Avatar avatar = Avatar.builder()
                .userId(userId)
                .tenantCode(tenantCode)
                .filename(StringUtils.cleanPath(
                        Optional.ofNullable(file.getOriginalFilename()).orElse("avatar-" + System.currentTimeMillis())))
                .mimeType(file.getContentType())
                .size(file.getSize())
                .content(null)//content
                .build();

        return avatarRepository.save(avatar);
    }

    @Override
    @Transactional
    public void createDefaultAvatar(Long userId, String tenantCode) {
        byte[] defaultBytes = loadDefaultAvatar();
        Avatar avatar = Avatar.builder()
                .userId(userId)
                .tenantCode(tenantCode)
                .content(defaultBytes)
                .mimeType("image/jpeg")
                .filename("default-avatar.jpg")
                .size((long) defaultBytes.length)
                .build();
        // If exists, replace; otherwise save
        avatarRepository.findByUserIdAndTenantCode(userId, tenantCode)
                .ifPresentOrElse(existing -> {
                    existing.setContent(defaultBytes);
                    existing.setFilename("default-avatar.jpg");
                    existing.setMimeType("image/jpeg");
                    existing.setSize((long) defaultBytes.length);
                    avatarRepository.save(existing);
                }, () -> avatarRepository.save(avatar));
        log.debug("Default avatar created for userId={} tenantCode={}", userId, tenantCode);
    }

    @Override
    @Transactional
    public Avatar updateAvatar(Long userId, String tenantCode, MultipartFile file) {
        validateAvatarFile(file);
        byte[] bytes = processFile(file);
        String mime = Optional.ofNullable(file.getContentType()).orElse("application/octet-stream");
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("avatar");

        Avatar avatar = avatarRepository.findByUserIdAndTenantCode(userId, tenantCode)
                .orElse(Avatar.builder()
                        .userId(userId)
                        .tenantCode(tenantCode)
                        .build());

        avatar.setContent(bytes);
        avatar.setMimeType(mime);
        avatar.setFilename(filename);
        avatar.setSize(file.getSize());
        avatarRepository.save(avatar);
        log.info("Avatar updated for userId={} tenantCode={}", userId, tenantCode);
        return avatar;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getAvatar(Long userId, String tenantCode) {
        return avatarRepository.findByUserIdAndTenantCode(userId, tenantCode)
                .map(Avatar::getContent)
                .orElseGet(this::loadDefaultAvatar);
    }

    @Override
    @Transactional(readOnly = true)
    public Avatar getAvatarEntity(Long userId, String tenantCode) {
        return avatarRepository.findByUserIdAndTenantCode(userId, tenantCode)
                .orElseThrow(() -> new InvalidAvatarException("error.avatar.notFound", userId));
    }

    @Override
    @Transactional
    public void deleteAvatar(Long userId, String tenantCode) {
        avatarRepository.deleteByUserIdAndTenantCode(userId, tenantCode);
        log.info("Avatar deleted for userId={} tenantCode={}", userId, tenantCode);
    }

    // ===== Helpers =====
    private void validateAvatarFile(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new InvalidAvatarException("error.avatar.validation", "null");
        String ct = file.getContentType();
        if (ct == null || !avatarProperties.getAllowedTypes().contains(ct)) {
            throw new InvalidAvatarException("error.avatar.allowedTypes", String.join(", ", avatarProperties.getAllowedTypes()));
        }
        if (file.getSize() > avatarProperties.getMaxSize()) {
            throw new InvalidAvatarException("error.avatar.maxSize", file.getSize());
        }
    }

    private byte[] processFile(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new InvalidAvatarException("error.avatar.processing", file.getOriginalFilename());
        }
    }

    private byte[] loadDefaultAvatar() {
        try {
            Resource resource = resourceLoader.getResource(avatarProperties.getDefaultPath());
            return StreamUtils.copyToByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.error("Failed to load default avatar from {}", avatarProperties.getDefaultPath(), e);
            return new byte[0];
        }
    }


//    @Override
//    public void updateAvatar(Long userId, MultipartFile file) {
//        validateAvatarFile(file);
//
//        byte[] avatarBytes = processImage(file);
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
//
//        user.setAvatar(avatarBytes);
//        userRepository.save(user);
//
//        log.info("Avatar actualizado para el usuario con ID: {}", userId);
//    }

//    @Override
//    public byte[] getAvatar(Long userId) {
//        return userRepository.findById(userId)
//                .map(User::getAvatar)
//                .filter(Objects::nonNull)
//                .orElseGet(this::getDefaultAvatar);
//    }

//    @Override
//    public void deleteAvatar(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));
//        user.setAvatar(null);
//        userRepository.save(user);
//        log.info("Avatar eliminado para el usuario con ID: {}", userId);
//    }

//    @Override
//    public byte[] getDefaultAvatar() {
//        try {
//            Resource resource = resourceLoader.getResource(avatarProperties.getDefaultPath());
//            return StreamUtils.copyToByteArray(resource.getInputStream());
//        } catch (IOException e) {
//            log.error("No se pudo cargar el avatar por defecto", e);
//            return new byte[0]; // Evita errores de frontend, aunque preferible lanzar excepción si es crítico
//        }
//    }

//    private void validateAvatarFile(MultipartFile file) {
//        if (file == null || file.isEmpty()) {
//            throw new InvalidAvatarException("El archivo de avatar está vacío.");
//        }
//
//        String mimeType = file.getContentType();
//        if (mimeType == null || !avatarProperties.getAllowedTypes().contains(mimeType)) {
//            throw new InvalidAvatarException("Tipo de archivo no permitido: " + mimeType);
//        }
//
//        if (file.getSize() > avatarProperties.getMaxSize()) {
//            throw new InvalidAvatarException("El archivo de avatar supera el tamaño máximo permitido.");
//        }
//    }

//    private byte[] processImage(MultipartFile file) {
//        try {
//            return file.getBytes(); // Podrías integrar Thumbnailator para resize
//        } catch (IOException e) {
//            throw new InvalidAvatarException("No se pudo procesar la imagen", e);
//        }
//    }
}
