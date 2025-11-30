package com.amachi.app.vitalia.superadmin.controller;

import com.amachi.app.vitalia.superadmin.dto.*;
import com.amachi.app.vitalia.superadmin.service.TenantManagementService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/tenants")
@RequiredArgsConstructor
public class TenantManagementController {

    private final TenantManagementService tenantService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Crear tenant + tenant admin (solo SUPER_ADMIN)")
    public ResponseEntity<TenantResponse> createTenant(@Valid @RequestBody TenantCreateRequest req) {
        return ResponseEntity.ok(tenantService.createTenantWithAdmin(req));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Listar tenants (paged)")
    public ResponseEntity<Page<TenantResponse>> listTenants(Pageable pageable) {
        return ResponseEntity.ok(tenantService.listTenants(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Obtener información de un tenant")
    public ResponseEntity<TenantResponse> getTenant(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenant(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "Actualizar tenant")
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable Long id, @Valid @RequestBody TenantUpdateRequest update) {
        return ResponseEntity.ok(tenantService.updateTenant(id, update));
    }

    @PatchMapping("/{id}/enable")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> enable(@PathVariable Long id) {
        tenantService.enableTenant(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/disable")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        tenantService.disableTenant(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/admin/reset-password")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> resetAdminPassword(@PathVariable Long id, @RequestParam(required = false) String newPassword) {
        tenantService.resetAdminPassword(id, newPassword != null ? newPassword : java.util.UUID.randomUUID().toString().substring(0,12));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        tenantService.softDeleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}

