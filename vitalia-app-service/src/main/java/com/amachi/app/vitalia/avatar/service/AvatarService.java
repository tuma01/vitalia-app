package com.amachi.app.vitalia.avatar.service;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    void updateAvatar(Long userId, MultipartFile file);

    byte[] getAvatar(Long userId);

    byte[] getAvatarByEmail(String email);

    void deleteAvatar(Long userId);

    byte[] getDefaultAvatar();
}
