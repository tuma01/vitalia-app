package com.amachi.app.core.auth.service;


import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;

import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> findByUserAndTenantCode(User user, String tenantCode);
}
