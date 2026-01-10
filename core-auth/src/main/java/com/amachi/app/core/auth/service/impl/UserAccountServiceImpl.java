package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.entity.User;
import com.amachi.app.core.auth.entity.UserAccount;
import com.amachi.app.core.auth.repository.UserAccountRepository;
import com.amachi.app.core.auth.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public Optional<UserAccount> findByUserAndTenantCode(User user, String tenantCode) {
        return userAccountRepository.findByUserAndTenantCode(user, tenantCode);
    }
}
