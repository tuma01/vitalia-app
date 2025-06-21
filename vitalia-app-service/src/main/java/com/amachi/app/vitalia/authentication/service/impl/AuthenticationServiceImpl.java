package com.amachi.app.vitalia.authentication.service.impl;

import com.amachi.app.vitalia.authentication.entity.Token;
import com.amachi.app.vitalia.authentication.entity.User;
import com.amachi.app.vitalia.authentication.repository.TokenRepository;
import com.amachi.app.vitalia.authentication.repository.UserRepository;
import com.amachi.app.vitalia.common.exception.ResourceNotFoundException;
import com.amachi.app.vitalia.common.utils.EmailTemplateName;

import com.amachi.app.vitalia.authentication.dto.AuthenticationRequest;
import com.amachi.app.vitalia.authentication.dto.AuthenticationResponse;
import com.amachi.app.vitalia.authentication.dto.UserRegisterDto;
import com.amachi.app.vitalia.entities.*;
import com.amachi.app.vitalia.repository.*;
import com.amachi.app.vitalia.authentication.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

    private final PersonRepository personRepository;

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    @Transactional
    public void register(UserRegisterDto input) throws MessagingException {
//        Optional<Role> roleOptional= roleRepository.findByName(input.getPersonType().getDefaultRole());
        Role defaultRole = roleRepository.findByName(input.getPersonType().getDefaultRole())
                .orElse(new Role("ROLE_USER"));

        // Inicializar roles con el rol por defecto
        Set<Role> userRoles = new HashSet<>(Collections.singleton(defaultRole));

        // Agregar roles adicionales si están definidos
        if (input.getRoles() != null && !input.getRoles().isEmpty()) {
            if (input.getRoles().contains("ROLE_SUPER_ADMIN")) {
                userRoles.clear(); // Elimina todos los roles si se asigna "ROLE_SUPER_ADMIN"
            }

            userRoles.addAll(input.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new IllegalStateException("Rol no encontrado: " + roleName)))
                    .collect(Collectors.toSet()));
        }

        Person person = personRepository.save(
                Person.builder()
                        .nombre(input.getFirstName())
                        .apellidoPaterno(input.getLastName())
                        .nombreCompleto(input.getFirstName() + " " + input.getLastName())
                        .personType(input.getPersonType())
                        .build()
        );



        // Crear el usuario
        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(userRoles)
                .person(person)
                .avatar(buildAvatar(input.getAvatar()))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private byte[] buildAvatar(byte[] avatar) {
        try {
            if (avatar == null) {
                return getDefaultAvatarBytes();
            }
            return avatar;
        } catch (IOException e) {
            // TODO crear un error con ControllerAdvison y no un byte[0]
            log.error("Error al cargar la imagen por defecto", e);
            throw new ResourceNotFoundException("error.resource.not.found", "default-avatar.jpg");
//            return new byte[0];
        }
    }
    private byte[] getDefaultAvatarBytes() throws IOException {
        //TODO a finalisar el avatar por defecto
        ClassPathResource defaultAvatar = new ClassPathResource("templates/avatars/default-avatar.jpg");
        return Files.readAllBytes(defaultAvatar.getFile().toPath());
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
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .avatar(user.getAvatar())
                .build();
        var jwtToken = jwtService.generateToken(claims, user);
        log.info("TOKEN : " + jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).userRegister(userRegister).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new IllegalStateException("Invalid Token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new IllegalStateException("Activation Token has expired. A new token has been sent to your email");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}