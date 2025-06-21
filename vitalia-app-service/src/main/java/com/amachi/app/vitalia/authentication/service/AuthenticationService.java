package com.amachi.app.vitalia.authentication.service;

import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    void register(UserRegisterDto input) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);

    @Transactional
    void activateAccount(String token) throws MessagingException;
}
