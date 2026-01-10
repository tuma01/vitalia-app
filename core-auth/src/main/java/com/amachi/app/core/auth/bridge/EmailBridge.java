package com.amachi.app.core.auth.bridge;


public interface EmailBridge {
    void sendActivationEmail(
            String to,
            String username,
            String activationCode
    );

//    emailBridge.sendActivationEmail(
//            user.getEmail(),
//            request.getEmail(), // o nombre de usuario si lo tienes
//    activationUrl,
//    token
}
