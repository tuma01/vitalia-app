package com.amachi.app.vitalia.authentication.controller;

import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.authentication.service.impl.AuthenticationServiceImpl;
import com.amachi.app.vitalia.authentication.service.impl.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws MessagingException {
        authenticationService.register(userRegisterDto);

        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest) {

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/activate-account")
    public void activateAccount(@RequestParam(name = "token") String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }
}