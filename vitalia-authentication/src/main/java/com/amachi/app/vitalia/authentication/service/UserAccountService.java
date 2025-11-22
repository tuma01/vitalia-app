package com.amachi.app.vitalia.authentication.service;


import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;

import java.util.Optional;

public interface UserAccountService {

    Optional<UserAccount> findByUserAndTenantCode(User user, String tenantCode);
}
