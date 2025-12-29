package com.amachi.app.vitalia.authentication.controller;

import com.amachi.app.vitalia.authentication.dto.PasswordResetConfirmationRequest;
import com.amachi.app.vitalia.authentication.service.AccountService;
import com.amachi.app.vitalia.authentication.dto.ActivationRequest;
import com.amachi.app.vitalia.authentication.dto.ChangePasswordRequest;
import com.amachi.app.vitalia.authentication.dto.PasswordResetRequest;
import com.amachi.app.vitalia.common.dto.UserSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account", description = "Gestión de cuentas de usuario (activación, contraseñas, perfil)")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final com.amachi.app.vitalia.authentication.service.TokenService tokenService;

    @Operation(summary = "Activar cuenta", description = "Activa una cuenta de usuario a partir del token de activación")
    @PostMapping(value = "/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activate(@Valid @RequestBody ActivationRequest request) {
        log.info("🔐 Activando cuenta con token: {}", request.getToken());
        accountService.activateAccount(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Solicitar restablecimiento de contraseña", description = "Envía un token o enlace para restablecer la contraseña")
    @PostMapping(value = "/request-reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        log.info("📩 Solicitud de restablecimiento de contraseña para: {}", request.getEmail());
        accountService.requestPasswordReset(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Restablecer contraseña", description = "Permite restablecer la contraseña mediante un token recibido por correo")
    @PostMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody PasswordResetConfirmationRequest request) {
        log.info("🔄 Restableciendo contraseña para token: {}", request.getToken());
        accountService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cambiar contraseña", description = "Permite al usuario autenticado cambiar su contraseña actual")
    @PostMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestHeader(org.springframework.http.HttpHeaders.AUTHORIZATION) String authHeader) {
        log.info("🔑 Cambio de contraseña solicitado por usuario ID: {}", userId);
        accountService.changePassword(request, userId);

        // Invalidar el token actual inmediatamente para forzar re-login (o al menos
        // refresh)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenService.invalidateToken(token);
            log.info("⛔ Token invalidado tras cambio de contraseña para usuario ID: {}", userId);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener perfil de usuario", description = "Devuelve la información básica del usuario autenticado")
    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserSummaryDto> getProfile(@AuthenticationPrincipal(expression = "id") Long userId) {
        log.debug("👤 Solicitando perfil para usuario ID: {}", userId);
        return ResponseEntity.ok(accountService.getProfile(userId));
    }

    @Operation(summary = "Dar de baja cuenta", description = "Desactiva la cuenta del usuario autenticado y cierra la sesión")
    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteAccount(
            @AuthenticationPrincipal(expression = "id") Long userId,
            @RequestHeader(org.springframework.http.HttpHeaders.AUTHORIZATION) String authHeader) {
        log.warn("⛔ Solicitud de baja de cuenta para usuario ID: {}", userId);
        accountService.deleteAccount(userId);

        // Invalidar token actual
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenService.invalidateToken(token);
        }

        return ResponseEntity.noContent().build();
    }
}