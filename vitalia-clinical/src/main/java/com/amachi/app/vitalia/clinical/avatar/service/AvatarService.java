package com.amachi.app.vitalia.clinical.avatar.service;

import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.clinical.avatar.dto.search.AvatarSearchDto;
import com.amachi.app.vitalia.clinical.avatar.entity.Avatar;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService extends GenericService<Avatar, AvatarSearchDto> {

    void createDefaultAvatar(Long userId, String tenantCode);

    void updateAvatar(Long userId, String tenantCode, MultipartFile file);

    void deleteAvatar(Long userId, String tenantCode);

    byte[] getAvatar(Long userId, String tenantCode);
}
