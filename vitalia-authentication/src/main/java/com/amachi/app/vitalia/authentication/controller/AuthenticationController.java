package com.amachi.app.vitalia.authentication.controller;

import com.amachi.app.vitalia.authentication.service.AuthenticationService;
import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Gestión de autenticación de usuarios (login, registro, tokens)")
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Autenticar usuario", description = "Verifica las credenciales del usuario y devuelve el JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        log.info("🔐 Login attempt for user: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario dentro del tenant especificado")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        log.info("🆕 Registro de nuevo usuario: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @Operation(summary = "Refrescar token", description = "Genera un nuevo access token usando el refresh token válido")
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestParam("refreshToken") String refreshToken) {
        log.debug("♻️ Solicitando refresh token");
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @Operation(summary = "Cerrar sesión", description = "Invalida el refresh token actual")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("refreshToken") String refreshToken) {
        log.info("🚪 Logout solicitado con refresh token");
        authenticationService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Validar token", description = "Verifica si un JWT sigue siendo válido (útil para mantener sesión)")
    @GetMapping("/validate")
    public ResponseEntity<Void> validateToken(@RequestParam("token") String token) {
        authenticationService.validateToken(token);
        return ResponseEntity.noContent().build();
    }
}