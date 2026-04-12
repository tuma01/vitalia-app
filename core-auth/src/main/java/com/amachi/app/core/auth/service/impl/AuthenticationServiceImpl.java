package com.amachi.app.core.auth.service.impl;

import com.amachi.app.core.auth.auditevent.service.AuditService;
import com.amachi.app.core.auth.bridge.*;
import com.amachi.app.core.auth.dto.AuthenticationRequest;
import com.amachi.app.core.auth.dto.AuthenticationResponse;
import com.amachi.app.core.auth.dto.JwtUserDto;
import com.amachi.app.core.auth.dto.UserRegisterRequest;
import com.amachi.app.core.auth.entity.*;
import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.auth.repository.*;
import com.amachi.app.core.auth.service.*;
import com.amachi.app.core.common.dto.*;
import com.amachi.app.core.domain.repository.PersonTenantRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.*;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.common.utils.AppConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository           userRepository;
    private final UserAccountRepository    userAccountRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final TokenService             tokenService;
    private final RefreshTokenService      refreshTokenService;
    private final JwtService               jwtService;
    private final PasswordEncoder          passwordEncoder;
    private final AccountService           accountService;
    private final AuditService             auditService;
    private final AuthenticationManager    authenticationManager;
    private final TenantBridge             tenantBridge;
    private final PersonTenantRepository   personTenantRepository;

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public AuthenticationResponse register(UserRegisterRequest request) {
        log.info("📝 Registro de nuevo usuario email='{}'", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.user.already_exists", request.getEmail());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .tenantId("GLOBAL")
                .build();

        user = userRepository.save(user);

        UserSummaryDto userSummary = UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .build();

        return AuthenticationResponse.builder().user(userSummary).build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("🔐 Autenticación iniciada para email='{}' tenant='{}'", request.getEmail(), request.getTenantCode());

        if (request.getEmail() == null || request.getPassword() == null) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.auth.missing_credentials");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_USER_NOT_FOUND, "security.user.not_found", request.getEmail()));

        String effectiveTenantCode = resolveTenantForLogin(user, request.getTenantCode());
        Tenant tenant = tenantBridge.findByCode(effectiveTenantCode);

        if (Boolean.FALSE.equals(tenant.getIsActive())) {
            throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED, "security.tenant.disabled", tenant.getCode());
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception ex) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_CREDENTIALS, "security.auth.invalid_credentials");
        }

        UserAccount userAccount = validateTenantAccess(user, tenant);
        List<String> authorities = loadEffectiveAuthorities(userAccount);

        if (authorities.isEmpty()) {
            throw new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_roles_in_tenant", tenant.getCode());
        }

        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities));

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return buildAuthenticationResponse(user, tenant, authorities);
    }

    private String resolveTenantForLogin(User user, String tenantCodeFromRequest) {
        if (tenantCodeFromRequest != null && !tenantCodeFromRequest.isBlank()) {
            return tenantCodeFromRequest;
        }

        boolean isSuperAdmin = userTenantRoleRepository.findActiveRolesByUser(user).stream()
                .anyMatch(r -> r.equalsIgnoreCase("SUPER_ADMIN") || r.equalsIgnoreCase(AppConstants.Roles.ROLE_SUPER_ADMIN));

        if (isSuperAdmin) return "GLOBAL";
        throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.tenant.required_for_user");
    }

    private UserAccount validateTenantAccess(User user, Tenant tenant) {
        UserAccount account = userAccountRepository.findByUserAndTenantCode(user, tenant.getCode())
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_account_in_tenant", tenant.getCode()));

        if (!user.isEnabled()) throw new AppSecurityException(ErrorCode.SEC_USER_DISABLED, "security.user.disabled", user.getEmail());
        if (user.isAccountLocked()) throw new AppSecurityException(ErrorCode.SEC_USER_LOCKED, "security.user.locked", user.getEmail());

        return account;
    }

    private List<String> loadEffectiveAuthorities(UserAccount account) {
        List<String> roles = userTenantRoleRepository.findActiveRolesByUserAndTenant(account.getUser(), account.getTenant())
                .stream().map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name).collect(Collectors.toCollection(ArrayList::new));

        List<String> permissions = userTenantRoleRepository.findActivePermissionsNamesByUserAndTenant(account.getUser(), account.getTenant());
        roles.addAll(permissions);
        return roles.stream().distinct().collect(Collectors.toList());
    }

    private AuthenticationResponse buildAuthenticationResponse(User user, Tenant tenant, List<String> roles) {
        JwtUserDto jwtUser = JwtUserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .roles(roles)
                .build();

        TokenPairDto tokenPair = tokenService.generateAndStoreTokenPair(jwtUser, user, tenant);

        if (auditService != null) {
            try { auditService.registerEvent(AuditEventType.USER_LOGIN, user.getId(), tenant.getId(), "User logged in"); }
            catch (Exception ignored) {}
        }

        return AuthenticationResponse.builder()
                .tokens(tokenPair)
                .accessToken(tokenPair.getAccessToken())
                .refreshToken(tokenPair.getRefreshToken())
                .expiresIn(tokenPair.getExpiresIn())
                .user(buildUserSummary(user, tenant, roles))
                .build();
    }

    private UserSummaryDto buildUserSummary(User user, Tenant tenant, List<String> roles) {
        return UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .lastLogin(user.getLastLogin())
                // Legacy fields for Frontend backward compatibility
                .tenantId(tenant.getId())
                .tenantCode(tenant.getCode())
                .tenantName(tenant.getName())
                .roles(roles)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) {
        log.info("🔄 Refreshing access token");
        return refreshTokenService.findByToken(refreshToken)
                .map(token -> {
                    if (!token.isValid()) {
                        throw new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, "security.refreshToken.expired");
                    }
                    User user = token.getUser();
                    Tenant tenant = tenantBridge.findByCode(token.getTenantId());

                    UserAccount userAccount = validateTenantAccess(user, tenant);
                    List<String> authorities = loadEffectiveAuthorities(userAccount);

                    return buildAuthenticationResponse(user, tenant, authorities);
                })
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, "security.refreshToken.not_found"));
    }

    @Override
    @Transactional
    public void logout(String refreshToken, String accessToken) {
        if (accessToken != null && !accessToken.isBlank()) {
            try { tokenService.invalidateToken(accessToken); }
            catch (Exception ignored) {}
        }

        refreshTokenService.findByToken(refreshToken).ifPresent(token ->
            refreshTokenService.deleteByUserIdAndTenantId(token.getUser().getId(), token.getTenantId()));

        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional(readOnly = true)
    public void validateToken(String token) {
        if (!jwtService.validateToken(token)) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, "security.invalid.token");
        }
    }
}
