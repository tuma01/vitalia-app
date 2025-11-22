package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.UserAccountRepository;
import com.amachi.app.vitalia.authentication.service.UserAccountService;
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
