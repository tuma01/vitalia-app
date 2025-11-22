package com.amachi.app.vitalia.avatar.repository;

import com.amachi.app.vitalia.avatar.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByUserIdAndTenantCode(Long userId, String tenantCode);
    void deleteByUserIdAndTenantCode(Long userId, String tenantCode);
}
