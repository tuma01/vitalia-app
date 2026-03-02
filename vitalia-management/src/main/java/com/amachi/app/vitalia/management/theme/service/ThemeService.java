package com.amachi.app.vitalia.management.theme.service;

import com.amachi.app.core.common.enums.ThemeMode;
import com.amachi.app.core.common.service.GenericService;
import com.amachi.app.core.domain.theme.dto.ThemeDto;
import com.amachi.app.core.domain.theme.dto.search.ThemeSearchDto;
import com.amachi.app.core.domain.theme.entity.Theme;
import com.amachi.app.vitalia.management.theme.mapper.ThemeMapper;
import com.amachi.app.vitalia.management.theme.repository.ThemeRepository;
import com.amachi.app.vitalia.management.theme.specification.ThemeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;
import com.amachi.app.core.common.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ENTITY_MUST_NOT_BE_NULL;
import static com.amachi.app.core.common.utils.AppConstants.ErrorMessages.ID_MUST_NOT_BE_NULL;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class ThemeService implements GenericService<Theme, ThemeSearchDto> {

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Theme> getAll() {
        return themeRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Theme> getAll(ThemeSearchDto searchDto, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("createdDate").descending());
        Specification<Theme> specification = new ThemeSpecification(searchDto);
        return themeRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Theme getById(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        return themeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Theme.class.getName(), "error.resource.not.found", id));
    }

    @Override
    @Transactional
    public Theme create(Theme entity) {
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        return themeRepository.save(entity);
    }

    @Override
    @Transactional
    public Theme update(Long id, Theme entity) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        requireNonNull(entity, ENTITY_MUST_NOT_BE_NULL);
        if (!themeRepository.existsById(id)) {
            throw new ResourceNotFoundException(Theme.class.getName(), "error.resource.not.found", id);
        }
        entity.setId(id);
        return themeRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        requireNonNull(id, ID_MUST_NOT_BE_NULL);
        if (!themeRepository.existsById(id)) {
            throw new ResourceNotFoundException(Theme.class.getName(), "error.resource.not.found", id);
        }
        themeRepository.deleteById(id);
    }

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
    public ThemeDto getThemeForTenant(String tenantCode) {
        return themeRepository.findByTenant_Code(tenantCode)
                .map(themeMapper::toDto)
                .orElseGet(() -> ThemeDto.builder()
                        .name("Default Theme")
                        .primaryColor("#3f51b5")
                        .accentColor("#ff4081")
                        .warnColor("#f44336")
                        .themeMode(ThemeMode.LIGHT)
                        .logoUrl("")
                        .faviconUrl("favicon.ico")
                        .build());
    }

    @Transactional
    public ThemeDto upgradeTheme(String tenantCode, ThemeDto dto) {
        Theme theme = themeRepository.findByTenant_Code(tenantCode)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found for tenant: " + tenantCode));

        themeMapper.updateEntityFromDto(dto, theme);

        return themeMapper.toDto(themeRepository.save(theme));
    }

    @Transactional
    public ThemeDto uploadLogo(String tenantCode, MultipartFile file) {
        Theme theme = themeRepository.findByTenant_Code(tenantCode)
                .orElseThrow(() -> new EntityNotFoundException("Theme not found for tenant: " + tenantCode));

        // TODO: Implement actual storage (S3/MinIO/FileSystem)
        // For now, we simulate a URL assuming the file is served from somewhere
        String fakeUrl = "/assets/uploads/" + tenantCode + "/logo_" + UUID.randomUUID() + ".png";

        theme.setLogoUrl(fakeUrl);
        return themeMapper.toDto(themeRepository.save(theme));
    }
}
