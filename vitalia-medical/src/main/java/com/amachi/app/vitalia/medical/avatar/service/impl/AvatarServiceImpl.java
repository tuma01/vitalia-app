package com.amachi.app.vitalia.medical.avatar.service.impl;

import com.amachi.app.core.common.exception.ResourceNotFoundException;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.vitalia.medical.avatar.dto.search.AvatarSearchDto;
import com.amachi.app.vitalia.medical.avatar.entity.Avatar;
import com.amachi.app.vitalia.medical.avatar.exception.InvalidAvatarException;
import com.amachi.app.vitalia.medical.avatar.repository.AvatarRepository;
import com.amachi.app.vitalia.medical.avatar.service.AvatarService;
import com.amachi.app.vitalia.medical.avatar.specification.AvatarSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarServiceImpl implements AvatarService, GenericService<Avatar, AvatarSearchDto> {

    private final AvatarRepository avatarRepository;

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public List<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Page<Avatar> getAll(@NonNull AvatarSearchDto searchDto, @NonNull Integer pageIndex,
            @NonNull Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        Specification<Avatar> specification = new AvatarSpecification(searchDto);
        return avatarRepository.findAll(specification, pageable);
    }

    @Override
    @NonNull
    @Transactional(readOnly = true)
    public Avatar getById(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return avatarRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Avatar.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @NonNull
    @Transactional
    public Avatar create(@NonNull Avatar entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return avatarRepository.save(entity);
    }

    @Override
    @NonNull
    @Transactional
    public Avatar update(@NonNull Long id, @NonNull Avatar entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        avatarRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Avatar.class.getName(), "error.resource.not.found", id));
        entity.setId(id);
        return avatarRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(@NonNull Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Avatar.class.getName(), "error.resource.not.found", id));
        avatarRepository.delete(avatar);
    }

    @Override
    @Transactional
    public void createDefaultAvatar(Long userId, String tenantId) {
        log.info("Creating default avatar for user [{}] in tenant [{}]", userId, tenantId);
        if (avatarRepository.findByUserIdAndTenantId(userId, tenantId).isEmpty()) {
            Avatar defaultAvatar = Avatar.builder()
                    .userId(userId)
                    .tenantId(tenantId)
                    .filename("default-avatar.png")
                    .mimeType("image/png")
                    .content(new byte[0])
                    .size(0L)
                    .build();
            avatarRepository.save(defaultAvatar);
        }
    }

    @Override
    @Transactional
    public void updateAvatar(Long userId, String tenantId, MultipartFile file) {
        log.info("Updating avatar for user [{}] in tenant [{}]", userId, tenantId);
        try {
            Avatar avatar = avatarRepository.findByUserIdAndTenantId(userId, tenantId)
                    .orElse(Avatar.builder().userId(userId).tenantId(tenantId).build());

            avatar.setFilename(file.getOriginalFilename());
            avatar.setMimeType(file.getContentType());
            avatar.setContent(file.getBytes());
            avatar.setSize(file.getSize());

            avatarRepository.save(avatar);
        } catch (IOException e) {
            log.error("Error reading avatar file", e);
            throw new InvalidAvatarException("Error processing avatar file", e);
        }
    }

    @Override
    @Transactional
    public void deleteAvatar(Long userId, String tenantId) {
        log.info("Deleting avatar for user [{}] in tenant [{}]", userId, tenantId);
        avatarRepository.deleteByUserIdAndTenantId(userId, tenantId);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] getAvatar(Long userId, String tenantId) {
        return avatarRepository.findByUserIdAndTenantId(userId, tenantId)
                .map(Avatar::getContent)
                .orElse(null);
    }
}
