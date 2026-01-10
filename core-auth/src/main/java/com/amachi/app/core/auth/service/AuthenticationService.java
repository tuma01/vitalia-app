package com.amachi.app.core.auth.service;

import com.amachi.app.core.auth.dto.AuthenticationRequest;
import com.amachi.app.core.auth.dto.AuthenticationResponse;
import com.amachi.app.core.auth.dto.UserRegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(UserRegisterRequest request);

    AuthenticationResponse refreshToken(String refreshToken);

    void logout(String refreshToken, String accessToken);

    void validateToken(String token);
}
