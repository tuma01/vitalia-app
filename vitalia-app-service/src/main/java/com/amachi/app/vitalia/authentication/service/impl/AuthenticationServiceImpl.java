package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.hospital.entity.Hospital;
import com.amachi.app.vitalia.hospital.service.HospitalConfigService;
import com.amachi.app.vitalia.user.entity.Person;
import com.amachi.app.vitalia.authentication.entity.Token;
import com.amachi.app.vitalia.avatar.service.AvatarService;
import com.amachi.app.vitalia.role.entity.Role;
import com.amachi.app.vitalia.user.entity.User;
import com.amachi.app.vitalia.authentication.repository.TokenRepository;
import com.amachi.app.vitalia.common.exception.InvalidTokenException;
import com.amachi.app.vitalia.common.factory.PersonFactory;
import com.amachi.app.vitalia.common.utils.EmailTemplateName;

import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.common.utils.TokenErrorType;
import com.amachi.app.vitalia.repository.*;
import com.amachi.app.vitalia.authentication.service.AuthenticationService;
import com.amachi.app.vitalia.user.repository.UserRepository;
import com.amachi.app.vitalia.utils.AppConstants;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final TokenRepository tokenRepository;

    private final JwtService jwtService;

    private final PersonFactory personFactory;

    private final AvatarService avatarService;

    private final HospitalConfigService hospitalConfigService;

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    @Value("${app.avatars.default-path:" + AppConstants.Resources.DEFAULT_AVATAR_PATH + "}")
    private String defaultAvatarPath;

    @Transactional
    public void register(UserRegisterDto dto) throws MessagingException {
        // Crear la persona del tipo correspondiente
        Person person = personFactory.create(dto);
        // Obtener hospital por defecto
        Hospital hospitalPorDefecto = hospitalConfigService.getHospitalPorDefecto();
        // Asociar hospital a la persona
        person.setHospitalPrincipal(hospitalPorDefecto);
        person.getHospitales().add(hospitalPorDefecto);

        Role defaultRole = roleRepository
                .findByName("ROLE_" + dto.getPersonType().name())
                .orElseThrow(() -> new RuntimeException("Role no encontrado"));

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(false)
                .accountLocked(false)
                .person(person)
                .roles(Set.of(defaultRole))
                .avatar(Optional.ofNullable(dto.getAvatar())
                        .orElseGet(avatarService::getDefaultAvatar))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getPerson().getNombreCompleto(),
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);

        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getPerson().getNombreCompleto());
        var userRegister = UserRegisterDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getPerson().getNombre())
                .lastName(user.getPerson().getApellidoPaterno())
                .personType(user.getPerson().getPersonType())
                .avatar(user.getAvatar())
                .build();
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(jwtToken).userRegister(userRegister).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        log.info("Activando cuenta con token: {}", token);
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException(
                        TokenErrorType.INVALID_TOKEN,
                        "El token proporcionado no existe o es inválido"
                ));

        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            log.warn("Token expirado. Reenviando correo.");
            sendValidationEmail(savedToken.getUser());
            throw new InvalidTokenException(TokenErrorType.EXPIRED_TOKEN);
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        log.info("Habilitando usuario {}", user.getEmail());

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
        log.info("Cuenta activada correctamente.");
    }
}