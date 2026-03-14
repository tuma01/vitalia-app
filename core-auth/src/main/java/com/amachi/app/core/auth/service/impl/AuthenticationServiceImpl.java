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
import com.amachi.app.core.domain.entity.Person;
import com.amachi.app.core.domain.entity.PersonTenant;
import com.amachi.app.core.domain.repository.PersonRepository;
import com.amachi.app.core.domain.repository.PersonTenantRepository;
import com.amachi.app.core.domain.tenant.entity.Tenant;
import com.amachi.app.core.common.enums.*;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.common.utils.AppConstants;
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
        private final TenantBridge tenantBridge;
        private final UserTenantRoleRepository userTenantRoleRepository;
        private final AuthenticationManager authenticationManager;
        private final TokenService tokenService;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;
        private final PersonRepository personRepository;
        private final PersonTenantRepository personTenantRepository;
        private final PasswordEncoder passwordEncoder;
        private final AvatarBridge avatarBridge;
        private final UserAccountRepository userAccountRepository;
        private final RoleRepository roleRepository;
        private final EmailBridge emailBridge;
        private final ActivationTokenRepository activationTokenRepository;
        private final AuditService auditService;
        private final UserTenantRoleService userTenantRoleService;

        @Override
        @Transactional
        public AuthenticationResponse register(UserRegisterRequest request) {
                log.info("🆕 Registro solicitado para usuario [{}] en tenant [{}]", request.getEmail(),
                                request.getTenantCode());

                // 1️⃣ Validar duplicidad de email
                if (userRepository.existsByEmail(request.getEmail())) {
                        throw new AppSecurityException(
                                        ErrorCode.SEC_INVALID_OPERATION,
                                        "validation.email.already_in_use",
                                        request.getEmail());
                }

                // 2️⃣ Validar existencia del tenant
                Tenant tenant = tenantBridge.findByCode(request.getTenantCode());

                // 3️⃣ Crear la persona directamente
                Person person = Person.builder()
                                .nombre(request.getNombre())
                                .apellidoPaterno(request.getApellidoPaterno())
                                .apellidoMaterno(request.getApellidoMaterno())
                                .personType(request.getPersonType())
                                .createdBy("SYSTEM")
                                .createdDate(LocalDateTime.now())
                                .build();
                personRepository.save(person);
                log.info("👤 Persona creada exitosamente con ID [{}]", person.getId());

                // 4️⃣ Crear el User
                User user = User.builder()
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .enabled(false) // aún no activado
                                .accountLocked(false)
                                .person(person)
                                .build();
                userRepository.save(user);
                log.debug("🔹 Usuario creado con id {}", user.getId());

                // 5️⃣ Crear el UserAccount (user + tenant + person)
                UserAccount userAccount = UserAccount.builder()
                                .user(user)
                                .tenant(tenant)
                                .person(person)
                                .build();
                userAccountRepository.save(userAccount);
                log.debug("🔹 UserAccount creado con id {} para userId {} en tenant {}", userAccount.getId(),
                                user.getId(),
                                tenant.getCode());

                // 5.1 Vincular Person-Tenant en Core directamente
                PersonTenant pt = PersonTenant.builder()
                                .person(person)
                                .tenant(tenant)
                                .roleContext(RoleContext.valueOf(request.getPersonType().name())) // Assuming mapping
                                .relationStatus(RelationStatus.ACTIVE)
                                .dateRegistered(LocalDateTime.now())
                                .build();
                personTenantRepository.save(pt);
                log.debug("🔹 PersonTenant creado con id {}", pt.getId());

                // 6️⃣ Asignar rol por defecto según tipo de persona (ej: ROLE_DOCTOR)
                Role defaultRole = roleRepository.findByName("ROLE_" + request.getPersonType().name())
                                .orElseThrow(() -> new AppSecurityException(
                                                ErrorCode.SEC_INVALID_OPERATION,
                                                "security.role.not_found",
                                                request.getPersonType().name()));

                // <-- persist user_tenant_role aquí: asegurar UserTenantRole para el nuevo user
                // 6.1️⃣ Guardar en USER_TENANT_ROLE (nuevo mecanismo)
                userTenantRoleService.assignRolesToUserAndTenant(user, tenant, Set.of(defaultRole));

                // 7️⃣ Crear avatar por defecto vía bridge
                avatarBridge.createDefaultAvatar(user.getId(), tenant.getCode());

                // 8️⃣ Generar token de activación
                String token = generateAndSaveActivationToken(user, tenant);

                // 9️⃣ Enviar email de activación (link al frontend)
                emailBridge.sendActivationEmail(
                                user.getEmail(),
                                request.getEmail(), // o nombre de usuario si lo tienes
                                token);

                // 🔟 Retornar respuesta resumida (no se generan JWT ni refresh tokens hasta
                // activar cuenta)
                UserSummaryDto userSummary = UserSummaryDto.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .personName(request.getNombre() + " " + request.getApellidoPaterno())
                                .tenantId(tenant.getId())
                                .tenantCode(tenant.getCode())
                                .tenantName(tenant.getName())
                                .personType(request.getPersonType())
                                .roles(List.of(defaultRole.getName()))
                                .enabled(user.isEnabled())
                                .build();

                log.info("✅ Usuario [{}] registrado exitosamente en tenant [{}], pendiente activación", user.getEmail(),
                                tenant.getCode());

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
                        throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                                        "security.auth.missing_credentials");
                }

                // 2) Recuperar user por email (independiente de tenant)
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new AppSecurityException(
                                                ErrorCode.SEC_INVALID_OPERATION,
                                                "security.user.not_found",
                                                request.getEmail()));

                // 3) Determinar tenant efectivo
                String effectiveTenantCode = resolveTenantForLogin(user, request.getTenantCode());

                // 4) Cargar tenant y validar estado
                Tenant tenant = tenantBridge.findByCode(effectiveTenantCode);

                if (Boolean.FALSE.equals(tenant.getIsActive())) {
                        throw new AppSecurityException(ErrorCode.SEC_TENANT_DISABLED, "security.tenant.disabled",
                                        tenant.getCode());
                }

                // 5) Autenticar credenciales
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(request.getEmail(),
                                                        request.getPassword()));
                } catch (Exception ex) {
                        log.warn("❌ Credenciales inválidas para {} : {}", request.getEmail(), ex.getMessage());
                        throw new AppSecurityException(ErrorCode.SEC_INVALID_CREDENTIALS,
                                        "security.auth.invalid_credentials");
                }

                // 6) Validar cuenta
                UserAccount userAccount = validateTenantAccess(user, tenant);

                // 7) Cargar Autoridades Efectivas (Roles + Permisos)
                List<String> authorities = loadEffectiveAuthorities(userAccount);

                if (authorities.isEmpty()) {
                        throw new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_roles_in_tenant",
                                        tenant.getCode());
                }

                // 7.1) Poner en Security Context
                List<GrantedAuthority> grantedAuthorities = authorities.stream()
                                .map(SimpleGrantedAuthority::new).collect(Collectors.toUnmodifiableList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                                grantedAuthorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);

                // 8) Actualizar lastLogin
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);

                // 9) Construir respuesta delegando a TokenService
                return buildAuthenticationResponse(user, tenant, authorities);
        }

        /* -------------------- auxiliares -------------------- */

        private String resolveTenantForLogin(User user, String tenantCodeFromRequest) {
                // Si se recibió tenantCode explícito, lo usamos (se validará después)
                if (tenantCodeFromRequest != null && !tenantCodeFromRequest.isBlank()) {
                        return tenantCodeFromRequest;
                }

                // Si no se envió tenantCode: permitir solo para superadmins → usan GLOBAL
                boolean isSuperAdmin = userTenantRoleRepository.findActiveRolesByUser(user).stream()
                                .anyMatch(r -> r.equalsIgnoreCase("SUPER_ADMIN")
                                                || r.equalsIgnoreCase(AppConstants.Roles.ROLE_SUPER_ADMIN));

                if (isSuperAdmin) {
                        return "GLOBAL";
                }

                // Si no es superadmin y no envió tenantCode → error
                throw new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION, "security.tenant.required_for_user");
        }

        private UserAccount validateTenantAccess(User user, Tenant tenant) {
                // Buscar UserAccount por user + tenant
                Optional<UserAccount> maybeAccount = userAccountRepository.findByUserAndTenantCode(user,
                                tenant.getCode());

                if (maybeAccount.isEmpty()) {
                        log.warn("⚠️ Usuario {} no tiene cuenta en tenant {}", user.getEmail(), tenant.getCode());
                        throw new AppSecurityException(ErrorCode.SEC_FORBIDDEN, "security.user.no_account_in_tenant",
                                        tenant.getCode());
                }

                UserAccount account = maybeAccount.get();

                // Validaciones de estado
                if (!user.isEnabled()) {
                        throw new AppSecurityException(ErrorCode.SEC_USER_DISABLED, "security.user.disabled",
                                        user.getEmail());
                }
                if (user.isAccountLocked()) {
                        throw new AppSecurityException(ErrorCode.SEC_USER_LOCKED, "security.user.locked",
                                        user.getEmail());
                }

                return account;
        }

        private List<String> loadEffectiveAuthorities(UserAccount account) {
                // 1. Obtener Roles
                List<String> roles = userTenantRoleRepository
                                .findActiveRolesByUserAndTenant(account.getUser(), account.getTenant())
                                .stream()
                                .map(name -> name.startsWith("ROLE_") ? name : "ROLE_" + name)
                                .collect(Collectors.toList());

                // 2. Obtener Permisos Granulares
                List<String> permissions = userTenantRoleRepository.findActivePermissionsNamesByUserAndTenant(
                                account.getUser(),
                                account.getTenant());

                // 3. Combinar (Roles + Permisos)
                roles.addAll(permissions);

                return roles.stream().distinct().toList();
        }

        private AuthenticationResponse buildAuthenticationResponse(User user, Tenant tenant,
                        List<String> roles) {
                // Construir JWT payload DTO
                JwtUserDto jwtUser = JwtUserDto.builder()
                                .userId(user.getId())
                                .email(user.getEmail())
                                .tenantCode(tenant.getCode())
                                .roles(roles)
                                .build();

                // 🔥 Delegación Óptima: TokenService maneja la generación y persistencia
                TokenPairDto tokenPair = tokenService.generateAndStoreTokenPair(jwtUser, user, tenant);

                // Registrar auditoría (si tienes auditService)
                if (auditService != null) {
                        try {
                                auditService.registerEvent(AuditEventType.USER_LOGIN, user.getId(), tenant.getId(),
                                                "User logged in");
                        } catch (Exception ignored) {
                        }
                }

                // Construir user summary manualmente
                UserSummaryDto summary = UserSummaryDto.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .personName(user.getPerson() != null ? user.getPerson().getNombreCompleto() : null)
                                .personType(user.getPerson() != null ? user.getPerson().getPersonType() : null)
                                .tenantId(tenant.getId())
                                .tenantCode(tenant.getCode())
                                .tenantName(tenant.getName())
                                .roles(roles)
                                .build();

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
                                .orElseThrow(() -> new AppSecurityException(ErrorCode.SEC_INVALID_OPERATION,
                                                "security.token.invalid_token"));

                User user = existingToken.getUser();
                Tenant tenant = existingToken.getTenant();

                List<String> roles = userTenantRoleRepository.findActiveRolesByUserAndTenantCode(user,
                                tenant.getCode());

                UserSummaryDto userSummary = UserSummaryDto.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .personName(user.getPerson() != null ? user.getPerson().getNombreCompleto() : null)
                                .personType(user.getPerson() != null ? user.getPerson().getPersonType() : null)
                                .tenantId(tenant.getId())
                                .tenantCode(tenant.getCode())
                                .tenantName(tenant.getName())
                                .roles(roles)
                                .build();

                return AuthenticationResponse.builder()
                                .tokens(newTokenPair)
                                .user(userSummary)
                                .build();
        }

        @Override
        @Transactional
        public void logout(String refreshToken, String accessToken) {
                log.info("🚪 Cerrando sesión. Invalida Access Token y borra Refresh Tokens.");

                // 1. Invalidar Access Token (Blacklist)
                if (accessToken != null && !accessToken.isBlank()) {
                        try {
                                tokenService.invalidateToken(accessToken);
                                log.debug("☑️ Access Token invalidado.");
                        } catch (Exception e) {
                                log.warn("⚠️ Error al invalidar access token en logout: {}", e.getMessage());
                        }
                }

                // 2. Borrar Refresh Tokens del usuario
                refreshTokenService.findByToken(refreshToken).ifPresent(token -> {
                        refreshTokenService.deleteByUserIdAndTenantId(
                                        token.getUser().getId(),
                                        token.getTenant().getId());
                        log.debug("☑️ Refresh tokens eliminados para user {} en tenant {}",
                                        token.getUser().getEmail(), token.getTenant().getCode());
                });

                // 3. Limpiar contexto
                SecurityContextHolder.clearContext();
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
                // try {
                // String activationLink = activationUrl + "?token=" + token;
                //
                // emailService.sendEmail(
                // user.getEmail(),
                // user.getPersonName() != null ? user.getPersonName() : user.getEmail(),
                // EmailTemplateName.ACTIVATION_ACCOUNT,
                // activationLink,
                // token,
                // "Account activation"
                // );
                //
                // log.info("📩 Email de activación enviado a {}", user.getEmail());
                // } catch (Exception e) {
                // log.error("❌ Error al enviar correo de activación a {}: {}", user.getEmail(),
                // e.getMessage());
                // throw new AppSecurityException(
                // ErrorCode.SEC_ACCOUNT_ACTIVATION_FAILED,
                // "security.activation_email_failed",
                // user.getEmail()
                // );
                // }
        }
}
