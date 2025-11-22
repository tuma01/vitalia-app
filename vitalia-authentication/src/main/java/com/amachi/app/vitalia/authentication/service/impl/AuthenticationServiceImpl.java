package com.amachi.app.vitalia.authentication.service.impl;

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
import com.amachi.app.vitalia.authentication.service.AuthenticationService;
import com.amachi.app.vitalia.authentication.service.JwtService;
import com.amachi.app.vitalia.authentication.service.RefreshTokenService;
import com.amachi.app.vitalia.authentication.service.TokenService;
import com.amachi.app.vitalia.common.dto.*;
import com.amachi.app.vitalia.common.entity.Tenant;
import com.amachi.app.vitalia.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
//        PersonTenant personTenant = new PersonTenant();
//        personTenant.setPerson(person);
//        personTenant.setTenant(tenant);
//        personTenant.setDateRegistered(LocalDateTime.now());
//        personTenant.setRelationStatus(RelationStatus.ACTIVE);

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
        log.info("🔐 Autenticando usuario [{}] en tenant [{}]", request.getEmail(), request.getTenantCode());
        // 1️⃣ Validar Tenant
        Tenant tenant = tenantRepository.findByCode(request.getTenantCode())
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_TENANT_NOT_FOUND,
                        "security.tenant.not_found",
                        request.getTenantCode()
                ));

        // 2️⃣ Autenticar credenciales con Spring Security
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            log.warn("🚫 Fallo de autenticación para [{}] en tenant [{}]: {}", request.getEmail(), tenant.getCode(), ex.getMessage());
            throw new AppSecurityException(
                    ErrorCode.SEC_INVALID_OPERATION,
                    "security.invalid_credentials",
                    request.getEmail()
            );
        }
        // 3️⃣ Buscar usuario
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppSecurityException(
                        ErrorCode.SEC_INVALID_OPERATION,
                        "security.user.not_found",
                        request.getEmail()
                ));

        // 4️⃣ Verificar roles activos del usuario en el tenant
        List<String> roles = userTenantRoleRepository.findActiveRolesByUserAndTenantCode(user, tenant.getCode());
        if (roles.isEmpty()) {
            log.warn("⚠️ Usuario [{}] no tiene roles activos en tenant [{}]", user.getEmail(), tenant.getCode());
            throw new AppSecurityException(
                    ErrorCode.SEC_FORBIDDEN,
                    "security.user.no_roles_in_tenant",
                    tenant.getCode()
            );
        }

        // 🕓 5️⃣ Actualizar fecha/hora de último login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // 6️⃣ Construcción del contexto JWT
        JwtUserDto jwtUserDto = JwtUserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .roles(roles)
                .build();

        // 7️⃣ Generación y persistencia de tokens
        TokenPairDto tokens = tokenService.generateAndStoreTokenPair(jwtUserDto, user, tenant);

        // 8️⃣ Preparar respuesta resumida
        UserSummaryDto userSummary = UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantCode(tenant.getCode())
                .roles(roles)
                .build();

        log.info("✅ Usuario [{}] autenticado exitosamente en tenant [{}]", user.getEmail(), tenant.getCode());
        return AuthenticationResponse.builder()
                .tokens(tokens)
                .user(userSummary)
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
     * Genera un código numérico aleatorio (para activación).
     */
    private String generateRandomNumericCode(int length) {
//        SecureRandom random = new SecureRandom();
//        String chars = "0123456789";
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            sb.append(chars.charAt(random.nextInt(chars.length())));
//        }
//        return sb.toString();
        return null;
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