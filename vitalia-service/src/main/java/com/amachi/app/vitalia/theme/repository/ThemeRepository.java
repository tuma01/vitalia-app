package com.amachi.app.vitalia.theme.repository;

import com.amachi.app.vitalia.common.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByTenant_Code(String tenantCode);
    Optional<Theme> findByTenant_Id(Long tenantId);
}
