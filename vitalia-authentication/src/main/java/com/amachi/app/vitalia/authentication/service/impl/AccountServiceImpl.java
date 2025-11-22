package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.dto.*;
import com.amachi.app.vitalia.authentication.entity.PasswordResetToken;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.entity.UserAccount;
import com.amachi.app.vitalia.authentication.repository.*;
import com.amachi.app.vitalia.authentication.service.AccountService;
import com.amachi.app.vitalia.authentication.service.JwtService;
import com.amachi.app.vitalia.common.dto.*;
//import com.amachi.app.vitalia.common.service.EmailService;
import com.amachi.app.vitalia.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


/**
 * Módulo: vitalia-security
 *
 * Implementación de la lógica de gestión de cuentas (activación, perfil, contraseñas).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final TenantRepository tenantRepository;
    private final UserTenantRoleRepository userTenantRoleRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
//    private final EmailService emailService;

    private static final Duration RESET_TOKEN_VALIDITY = Duration.ofMinutes(30);

    @Transactional
    public void activateAccount(ActivationRequest request) {
        JwtUserDto userDto = jwtService.extractUserDto(request.getToken());
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new SecurityException("User not found: " + userDto.getUserId()));

        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Account activated for user: {}", user.getEmail());
    }

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {

        // Validar que el tenant existe
        tenantRepository.findByCode(request.getTenantCode())
                .orElseThrow(() -> new SecurityException("Tenant not found: " + request.getTenantCode()));

        // Buscar UserAccount por email y tenant
        User user = userAccountRepository
                .findByUserEmailAndTenantCode(request.getEmail(), request.getTenantCode())
                .map(UserAccount::getUser)
                .orElseThrow(() -> new SecurityException(
                        "User not found with email: " + request.getEmail() + " in tenant: " + request.getTenantCode()
                ));

        // Eliminar tokens anteriores del mismo usuario
        passwordResetTokenRepository.deleteByUser(user);

        // Generar token JWT para reset
        JwtUserDto jwtUserDto = JwtUserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .tenantCode(request.getTenantCode())
                .build();

        String resetToken = jwtService.generatePasswordResetToken(jwtUserDto);

        PasswordResetToken tokenEntity  = PasswordResetToken.builder()
                .token(resetToken)
                .user(user)
                .expirationDate(Instant.now().plus(RESET_TOKEN_VALIDITY))
                .user(user)
                .build();

        passwordResetTokenRepository.save(tokenEntity);

        // Enviar correo
//        emailService.sendPasswordResetEmail(user.getEmail(), resetToken, request.getTenantCode());
        log.info("📩 Password reset token generated for {} in tenant {}", user.getEmail(), request.getTenantCode());
    }

    @Override
    @Transactional
    public void resetPassword(PasswordResetConfirmationRequest request) {
        log.info("🔄 Restableciendo contraseña para token: {}", request.getToken());

        PasswordResetToken tokenEntity = passwordResetTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new SecurityException("Invalid or expired reset token"));

        if (tokenEntity.isInvalid() || !jwtService.isPasswordResetToken(tokenEntity.getToken())) {
            throw new SecurityException("Token inválido o ya usado");
        }
        User user = tokenEntity.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        tokenEntity.setUsed(true); // marcar como usado
        passwordResetTokenRepository.save(tokenEntity);

        // Invalidar token en JWTService (opcional)
        jwtService.invalidateToken(tokenEntity.getToken());

        log.info("✅ Contraseña restablecida correctamente para usuario: {}", user.getEmail());
    }

    @Transactional
    public void confirmPasswordReset(PasswordResetConfirmationRequest request) {
        log.info("🔑 Confirming password reset for token: {}", request.getToken());

        // Buscar el token en la base de datos
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(request.getToken())
                .orElseThrow(() -> new SecurityException("Invalid or expired password reset token"));

        // Validar que no esté expirado
        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new SecurityException("Password reset token has expired");
        }

        User user = resetToken.getUser();

        // Cambiar la contraseña
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Eliminar el token después de usarlo
        passwordResetTokenRepository.delete(resetToken);

        // Opcional: invalidar token en JwtService si también es JWT
        if (jwtService.isPasswordResetToken(request.getToken())) {
            jwtService.invalidateToken(request.getToken());
        }

        log.info("✅ Password reset confirmed for user: {}", user.getEmail());
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SecurityException("User not found: " + userId));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new SecurityException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("Password changed for user: {}", user.getEmail());
    }

    @Transactional(readOnly = true)
    public UserSummaryDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SecurityException("User not found: " + userId));

        // Obtener tenant del contexto de seguridad
        String tenantCode = getCurrentTenantCode();
        List<String> roles = userTenantRoleRepository.findActiveRolesByUserAndTenantCode(user, tenantCode);

        return UserSummaryDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .tenantCode(tenantCode)
                .roles(roles)
                .build();
    }

    private String getCurrentTenantCode() {
        // Implementar según tu lógica de contexto de tenant
        return "default-tenant"; // Placeholder
    }
}