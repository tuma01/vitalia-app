package com.amachi.app.vitalia.authentication.service;

import com.amachi.app.vitalia.authentication.entity.Role;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserTenantRole;
import com.amachi.app.vitalia.common.entity.Tenant;

import java.util.List;
import java.util.Set;

public interface UserTenantRoleService {
//    void assignRolesToUser(Long userTenantId, List<String> roles);

    void assignRolesToUserAndTenant(Long userId, Long tenantId, List<String> roleNames);

    /**
     * Asigna las roles (entidades Role) al user en el tenant y persiste UserTenantRole.
     * Evita duplicados y devuelve la lista creada.
     */
    Set<UserTenantRole> assignRolesToUserAndTenant(User user, Tenant tenant, Set<Role> roles);
}
