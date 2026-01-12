package com.amachi.app.vitalia.clinical.avatar.bridge.impl;

import com.amachi.app.core.auth.bridge.AvatarBridge;
import com.amachi.app.vitalia.clinical.avatar.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class AvatarBridgeImpl implements AvatarBridge {

    private final AvatarService avatarService;

    @Override
    public void createDefaultAvatar(Long userId, String tenantCode) {
        avatarService.createDefaultAvatar(userId, tenantCode);
    }

    @Override
    public void updateAvatar(Long userId, String tenantCode, MultipartFile file) {
        avatarService.updateAvatar(userId, tenantCode, file);
    }

    @Override
    public void deleteAvatar(Long userId, String tenantCode) {
        avatarService.deleteAvatar(userId, tenantCode);
    }

    @Override
    public byte[] getAvatar(Long userId, String tenantCode) {
        return avatarService.getAvatar(userId, tenantCode);
    }
}
