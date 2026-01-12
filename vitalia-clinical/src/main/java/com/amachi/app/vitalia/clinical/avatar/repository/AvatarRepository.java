package com.amachi.app.vitalia.clinical.avatar.repository;

import com.amachi.app.vitalia.clinical.avatar.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long>, JpaSpecificationExecutor<Avatar> {

    Optional<Avatar> findByUserIdAndTenantCode(Long userId, String tenantCode);

    void deleteByUserIdAndTenantCode(Long userId, String tenantCode);
}
