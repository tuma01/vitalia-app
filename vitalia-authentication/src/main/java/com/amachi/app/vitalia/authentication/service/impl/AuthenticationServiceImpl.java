package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.auditevent.service.AuditService;
import com.amachi.app.vitalia.authentication.bridge.AvatarBridge;
import com.amachi.app.vitalia.authentication.bridge.EmailBridge;
import com.amachi.app.vitalia.authentication.bridge.PersonBridge;
import com.amachi.app.vitalia.authentication.bridge.PersonTenantBridge;
import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.JwtUserDto;
import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import com.amachi.app.vitalia.authentication.entity.*;
import com.amachi.app.vitalia.authentication.exception.AppSecurityException;
import com.amachi.app.vitalia.authentication.repository.*;
import com.amachi.app.vitalia.authentication.service.*;
import com.amachi.app.vitalia.common.dto.*;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.enums.AuditEventType;
import com.amachi.app.vitalia.common.error.ErrorCode;
import com.amachi.app.vitalia.common.utils.AppConstants;
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

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final PersonBridge personBridge;
    private final PersonTenantBridge personTenantBridge;
    private final PasswordEncoder passwordEncoder;
    private final AvatarBridge avatarBridge;
    private final UserAccountRepository userAccountRepository;
    private final RoleRepository roleRepository;
    private final EmailBridge emailBridge;
    private final ActivationTokenRepository activationTokenRepository;
    private final AuditService auditService;
    private final UserTenantRoleService userTenantRoleService;
//    private final PersonRepository

    @Override
    @Transactional
    public AuthenticationResponse register(UserRegisterRequest request) {
        log.info("🆕 Registro solicitado para usuario [{}] en tenant [{}]", request.getEmail(), request.getTenantCode());

        // 1️⃣ Validar duplicidad de email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppSecurityException(
                    ErrorCode.SEC_INVALID_OPERATION,
                    "validation.email.already_in_use",
                    request.getEmail()
            );
        }

        // 2️⃣ Validar existencia del tenant
        Tenant tenant = tenantRepository.findByCode(request.getTenantCode())
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_TENANT_NOT_FOUND,
                        "security.tenant.not_found",
                        request.getTenantCode()
                ));

        // 3️⃣ Crear la persona a través del bridge
        Long personId = personBridge.createPerson(request);
        if (personId == null || personId <= 0) {
            throw new AppSecurityException(
                    ErrorCode.SEC_INVALID_OPERATION,
                    "security.person.creation_failed"
            );
        }
        log.info("👤 Persona creada exitosamente con ID [{}]", personId);

        // 4️⃣ Crear el User
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false) // aún no activado
                .accountLocked(false)
                .personId(personId)
                .build();
        userRepository.save(user);
        log.debug("🔹 Usuario creado con id {}", user.getId());

        // 5️⃣ Crear el UserAccount (user + tenant + roles)
        UserAccount userAccount = UserAccount.builder()
                .user(user)
                .tenant(tenant)
                .personId(personId)
                .build();
        userAccountRepository.save(userAccount);
        log.debug("🔹 UserAccount creado con id {} para userId {} en tenant {}", userAccount.getId(), user.getId(), tenant.getCode());

        Long personTenantId = personTenantBridge.create(personId,request.getTenantCode(), request.getPersonType());

        // 6️⃣ Asignar rol por defecto según tipo de persona (ej: ROLE_DOCTOR)
        Role defaultRole = roleRepository.findByName("ROLE_" + request.getPersonType().name())
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_INVALID_OPERATION,
                        "security.role.not_found",
                        request.getPersonType().name()
                ));
        if (userAccount.getRoles() == null || userAccount.getRoles().isEmpty()) {
            userAccount.setRoles(new HashSet<>());
        }
        userAccount.getRoles().add(defaultRole);
        userAccountRepository.save(userAccount);

        // <-- persist user_tenant_role aquí: asegurar UserTenantRole para el nuevo user
        userTenantRoleService.assignRolesToUserAndTenant(user, tenant, Set.of(defaultRole));

        // 7️⃣ Crear avatar por defecto vía bridge
        avatarBridge.createDefaultAvatar(user.getId(), tenant.getCode());

        // 8️⃣ Generar token de activación
        String token = generateAndSaveActivationToken(user, tenant);

        // 9️⃣ Enviar email de activación (link al frontend)
        emailBridge.sendActivationEmail(
                user.getEmail(),
                request.getEmail(), // o nombre de usuario si lo tienes
                token
        );

        // 🔟 Retornar respuesta resumida (no se generan JWT ni refresh tokens hasta activar cuenta)
        UserSummaryDto userSummary = UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .personName(request.getNombre() + " " + request.getApellidoPaterno())
                .tenantCode(tenant.getCode())
                .personType(request.getPersonType())
                .roles(List.of(defaultRole.getName()))
                .enabled(user.isEnabled())
                .build();

        log.info("✅ Usuario [{}] registrado exitosamente en tenant [{}], pendiente activación", user.getEmail(), tenant.getCode());

        return AuthenticationResponse.builder()
                .tokens(null) // JWT solo tras activación
                .user(userSummary)
                .build();
    }


    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("🔐 Autenticación iniciada para email='{}' tenant='{}'",
                request.getEmail(), request.getTenantCode());

        // 1) Validación básica
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.auth.missing_credentials");
        }

        // 2) Recuperar user por email (independiente de tenant)
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_INVALID_OPERATION,
                        "security.user.not_found",
                        request.getEmail()
                ));

        // 3) Determinar tenant efectivo (aplica GLOBAL para superadmins si no se envió tenantCode)
        String effectiveTenantCode = resolveTenantForLogin(user, request.getTenantCode());

        // 4) Cargar tenant y validar estado
        Tenant tenant = tenantRepository.findByCode(effectiveTenantCode)
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_TENANT_NOT_FOUND,
                        "security.tenant.not_found",
                        effectiveTenantCode
                ));
        if (Boolean.FALSE.equals(tenant.getIsActive())) {
            throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED, "security.tenant.disabled", tenant.getCode());
        }

        // 5) Autenticar credenciales (delegar a Spring Security)
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (Exception ex) {
            log.warn("❌ Credenciales inválidas para {} : {}", request.getEmail(), ex.getMessage());
            throw new AppSecurityException(ErrorCode.SEC_INVALID_CREDENTIALS, "security.auth.invalid_credentials");
        }

        // 6) Validar que el usuario tenga cuenta en el tenant y obtener UserAccount
        UserAccount userAccount = validateTenantAccess(user, tenant);

        // 7) Normalizar roles desde UserAccount
        List<String> roles = normalizeRoles(userAccount);

        if (roles.isEmpty()) {
            throw new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_roles_in_tenant", tenant.getCode());
        }

        // 7.1) Convertir roles a GrantedAuthority y ponerlos en el contexto
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableList());

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 8) Actualizar lastLogin
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // 9) Construir respuesta: generar tokens y summary
        return buildAuthenticationResponse(user, userAccount, tenant, roles);
    }

    /* -------------------- auxiliares -------------------- */

    private String resolveTenantForLogin(User user, String tenantCodeFromRequest) {
        // Si se recibió tenantCode explícito, lo usamos (se validará después)
        if (tenantCodeFromRequest != null && !tenantCodeFromRequest.isBlank()) {
            return tenantCodeFromRequest;
        }

        // Si no se envió tenantCode: permitir solo para superadmins → usan GLOBAL
        boolean isSuperAdmin = userTenantRoleRepository.findActiveRolesByUser(user).stream()
                .anyMatch(r -> r.equalsIgnoreCase(AppConstants.Bootstrap.SUPER_ADMIN) || r.equalsIgnoreCase("ROLE_SUPER_ADMIN"));

        if (isSuperAdmin) {
            return "GLOBAL";
        }

        // Si no es superadmin y no envió tenantCode → error
        throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.tenant.required_for_user");
    }

    private UserAccount validateTenantAccess(User user, Tenant tenant) {
        // Buscar UserAccount por user + tenant
        Optional<UserAccount> maybeAccount = userAccountRepository.findByUserAndTenantCode(user, tenant.getCode());

        if (maybeAccount.isEmpty()) {
            log.warn("⚠️ Usuario {} no tiene cuenta en tenant {}", user.getEmail(), tenant.getCode());
            throw new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_account_in_tenant", tenant.getCode());
        }

        UserAccount account = maybeAccount.get();

        // Validaciones de estado
        if (!user.isEnabled()) {
            throw new AppSecurityException(ErrorCode.SEC_USER_DISABLED, "security.user.disabled", user.getEmail());
        }
        if (user.isAccountLocked()) {
            throw new AppSecurityException(ErrorCode.SEC_USER_LOCKED, "security.user.locked", user.getEmail());
        }

        return account;
    }

    private List<String> normalizeRoles(UserAccount account) {
        return account.getRoles().stream()
                .map(Role::getName)
                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name)
                .distinct()
                .toList();
    }

    private AuthenticationResponse buildAuthenticationResponse(User user, UserAccount account, Tenant tenant, List<String> roles) {
        // Construir JWT payload DTO
        JwtUserDto jwtUser = JwtUserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .roles(roles)
                .build();

        // Generar pair (access + refresh) usando tu JwtService impl
        TokenPairDto tokenPair = jwtService.generateTokenPair(jwtUser);

        // Persistir refresh token si tienes un servicio (opcional)
        if (refreshTokenService != null) {
            try {
                refreshTokenService.createRefreshToken(user.getId(),
                        tenant.getId(),
                        tokenPair.getRefreshToken());
            } catch (Exception ex) {
                log.warn("⚠️ No se pudo persistir refreshToken: {}", ex.getMessage());
                // No abortar autenticación por fallo de persistencia de refresh token salvo que lo desees
            }
        }

        // Registrar auditoría (si tienes auditService)
        if (auditService != null) {
            try {
                auditService.registerEvent(AuditEventType.USER_LOGIN, user.getId(), tenant.getId(),
                        "User logged in");
            } catch (Exception ignored) {
            }
        }

        // Construir user summary
        UserSummaryDto summary = personBridge.buildUserSummary(user, tenant);
        // 🔥 Sobrescribir roles desde los roles normalizados
        summary.setRoles(roles);

        // Responder
        return AuthenticationResponse.builder()
                .tokens(tokenPair)
                .user(summary)
                .build();
    }




    @Override
    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) {
        log.debug("♻️ Solicitando refresh de token...");

        TokenPairDto newTokenPair = tokenService.refreshTokenPair(refreshToken);

        // ⚠️ Recuperar usuario según el token
        RefreshToken existingToken = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.token.invalid_token"));

        User user = existingToken.getUser();
        Tenant tenant = existingToken.getTenant();

        List<String> roles = userTenantRoleRepository.findActiveRolesByUserAndTenantCode(user, tenant.getCode());

        UserSummaryDto userSummary = UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .roles(roles)
                .build();

        return AuthenticationResponse.builder()
                .tokens(newTokenPair)
                .user(userSummary)
                .build();
    }
    // TODO para realizar
    @Override
    @Transactional
    public void logout(String refreshToken) {
        log.info("🚪 Cerrando sesión para token: {}", refreshToken);

        refreshTokenService.findByToken(refreshToken).ifPresent(token -> {
            refreshTokenService.verifyExpiration(token);
            refreshTokenService.deleteByUserIdAndTenantId(
                    token.getUser().getId(),
                    token.getTenant().getId()
            );
        });
    }

    @Override
    @Transactional(readOnly = true)
    public void validateToken(String token) {
        log.debug("🧩 Validando token JWT...");
        if (!jwtService.validateToken(token)) {
            throw new AppSecurityException(ErrorCode.SEC_INVALID_TOKEN, "security.invalid.token");
        }
    }

    /**
     * Genera y guarda el token de activación temporal.
     */
    private String generateAndSaveActivationToken(User user, Tenant tenant) {
        String token = UUID.randomUUID().toString(); // más seguro que 6 dígitos

        ActivationToken activationToken = ActivationToken.builder()
                .token(token)
                .user(user)
                .tenant(tenant)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24)) // token válido 24h
                .used(false)
                .build();

        activationTokenRepository.save(activationToken);
        return token;
    }

    /**
     * Envía el correo de activación de cuenta al usuario.
     */
    private void sendActivationEmail(User user, String token) {
//        try {
//            String activationLink = activationUrl + "?token=" + token;
//
//            emailService.sendEmail(
//                    user.getEmail(),
//                    user.getPersonName() != null ? user.getPersonName() : user.getEmail(),
//                    EmailTemplateName.ACTIVATION_ACCOUNT,
//                    activationLink,
//                    token,
//                    "Account activation"
//            );
//
//            log.info("📩 Email de activación enviado a {}", user.getEmail());
//        } catch (Exception e) {
//            log.error("❌ Error al enviar correo de activación a {}: {}", user.getEmail(), e.getMessage());
//            throw new AppSecurityException(
//                    ErrorCode.SEC_ACCOUNT_ACTIVATION_FAILED,
//                    "security.activation_email_failed",
//                    user.getEmail()
//            );
//        }
    }
}