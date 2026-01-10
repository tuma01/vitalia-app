package com.amachi.app.vitalia.management.theme.repository;

import com.amachi.app.core.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByTenant_Code(String tenantCode);
    Optional<Theme> findByTenant_Id(Long tenantId);

    Optional<Theme> findByCode(String defaultTheme);
}
