package com.amachi.app.vitalia.theme.service;

import com.amachi.app.vitalia.common.enums.ThemeMode;
import com.amachi.app.vitalia.theme.dto.ThemeDTO;
import com.amachi.app.vitalia.common.entity.Theme;
import com.amachi.app.vitalia.theme.mapper.ThemeMapper;
import com.amachi.app.vitalia.theme.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    @Transactional
    public Theme createDefaultTheme(String tenantName) {
        // Create a default theme for a new tenant
        Theme theme = Theme.builder()
                .name(tenantName + " Default Theme")
                .primaryColor("#3f51b5") // Indigo
                .accentColor("#ff4081") // Pink
                .warnColor("#f44336") // Red
                .themeMode(ThemeMode.LIGHT)
                .build();
        return themeRepository.save(theme);
    }

    @Transactional(readOnly = true)
    public ThemeDTO getThemeById(Long id) {
        return themeRepository.findById(id)
                .map(themeMapper::toDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public ThemeDTO getThemeForTenant(String tenantCode) {
        return themeRepository.findByTenant_Code(tenantCode)
                .map(themeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found for tenant: " + tenantCode));
    }

    @Transactional
    public ThemeDTO updateTheme(String tenantCode, com.amachi.app.vitalia.theme.dto.TenantThemeUpdateRequest req) {
        Theme theme = themeRepository.findByTenant_Code(tenantCode)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found for tenant: " + tenantCode));

        themeMapper.updateThemeFromRequest(req, theme);

        return themeMapper.toDto(themeRepository.save(theme));
    }

    @Transactional
    public ThemeDTO uploadLogo(String tenantCode, MultipartFile file) {
        Theme theme = themeRepository.findByTenant_Code(tenantCode)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found for tenant: " + tenantCode));

        // TODO: Implement actual storage (S3/MinIO/FileSystem)
        // For now, we simulate a URL assuming the file is served from somewhere
        String fakeUrl = "/assets/uploads/" + tenantCode + "/logo_" + UUID.randomUUID() + ".png";

        theme.setLogoUrl(fakeUrl);
        return themeMapper.toDto(themeRepository.save(theme));
    }

}
