package com.amachi.app.vitalia.tenant.service;

import com.amachi.app.vitalia.common.entity.Tenant;

import java.util.List;

public interface TenantService {

    public List<Tenant> getAllTenants();

    public Tenant getTenantByCode(String code);
}
