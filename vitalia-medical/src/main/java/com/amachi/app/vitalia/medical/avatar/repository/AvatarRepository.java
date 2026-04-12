package com.amachi.app.vitalia.medical.avatar.repository;

import com.amachi.app.vitalia.medical.avatar.entity.Avatar;
import com.amachi.app.core.common.repository.CommonRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends CommonRepository<Avatar, Long> {

    Optional<Avatar> findByUserIdAndTenantId(Long userId, String tenantId);

    void deleteByUserIdAndTenantId(Long userId, String tenantId);
}
