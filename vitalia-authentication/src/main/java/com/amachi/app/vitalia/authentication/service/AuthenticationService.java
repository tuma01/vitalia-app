package com.amachi.app.vitalia.authentication.service;

import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse register(UserRegisterRequest request);

    AuthenticationResponse refreshToken(String refreshToken);

    void logout(String refreshToken, String accessToken);

    void validateToken(String token);
}
