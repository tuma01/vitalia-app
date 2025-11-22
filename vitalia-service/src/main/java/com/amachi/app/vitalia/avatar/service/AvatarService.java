package com.amachi.app.vitalia.avatar.service;

import com.amachi.app.vitalia.avatar.entity.Avatar;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    Avatar saveAvatar(MultipartFile file, Long userId, String tenantCode);
//    Avatar saveAvatar(Long userId, String tenantCode);

    void createDefaultAvatar(Long userId, String tenantCode);

    Avatar updateAvatar(Long userId, String tenantCode, MultipartFile file);

    byte[] getAvatar(Long userId, String tenantCode);

    Avatar getAvatarEntity(Long userId, String tenantCode);

    void deleteAvatar(Long userId, String tenantCode);

//    void updateAvatar(Long userId, MultipartFile file);
//    byte[] getAvatar(Long userId);
//    void deleteAvatar(Long userId);
}
