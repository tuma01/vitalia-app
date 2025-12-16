package com.amachi.app.vitalia.tenant.controller;

import com.amachi.app.vitalia.common.api.ApiResponse;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.http.HttpStatusCode;
import com.amachi.app.vitalia.tenant.service.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Tenant>>> getAllTenants(HttpServletRequest request) {
        List<Tenant> tenants = tenantService.getAllTenants();
        return ResponseEntity.ok(ApiResponse.success(tenants, request.getRequestURI(), HttpStatusCode.OK));
    }
}
