package com.amachi.app.vitalia.management.theme.repository;

import com.amachi.app.core.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long>, JpaSpecificationExecutor<Theme> {

    Optional<Theme> findByCode(String defaultTheme);

    java.util.List<Theme> findAllByIsTemplateTrue();
}
