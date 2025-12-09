package com.amachi.app.vitalia.theme.controller;

import com.amachi.app.vitalia.theme.dto.TenantThemeUpdateRequest;
import com.amachi.app.vitalia.theme.dto.ThemeDTO;
import com.amachi.app.vitalia.theme.service.ThemeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    // Public: returns theme by tenantCode (from path or header)
    @GetMapping("/{tenantCode}/theme")
    public ResponseEntity<ThemeDTO> getTheme(@PathVariable String tenantCode) {
        ThemeDTO dto = themeService.getThemeForTenant(tenantCode);
        return ResponseEntity.ok(dto);
    }

    // Alternate: resolve by header/domain: GET /api/v1/tenant/config (no tenantCode
    // param)
    // Removed specific TenantResolver logic to keep it simple as per request, but
    // kept the endpoint structure
    @GetMapping("/config")
    public ResponseEntity<ThemeDTO> getThemeByResolvedTenant(
            @RequestHeader(value = "X-Tenant-ID", required = true) String tenantCode) {
        return ResponseEntity.ok(themeService.getThemeForTenant(tenantCode));
    }

    // Update: only tenant admin or super-admin
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/{tenantCode}/theme")
    public ResponseEntity<ThemeDTO> updateTheme(@PathVariable String tenantCode,
            @Valid @RequestBody TenantThemeUpdateRequest req) {
        return ResponseEntity.ok(themeService.updateTheme(tenantCode, req));
    }

    // Upload logo (multipart): only ADMIN or SUPER_ADMIN
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/{tenantCode}/theme/logo")
    public ResponseEntity<ThemeDTO> uploadLogo(@PathVariable String tenantCode,
            @RequestParam("file") MultipartFile file) {
        ThemeDTO dto = themeService.uploadLogo(tenantCode, file);
        return ResponseEntity.ok(dto);
    }
}
