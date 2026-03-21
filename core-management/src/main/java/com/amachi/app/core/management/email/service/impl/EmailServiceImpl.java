package com.amachi.app.core.management.email.service.impl;

import com.amachi.app.core.auth.exception.AppSecurityException;
import com.amachi.app.core.common.error.ErrorCode;
import com.amachi.app.core.management.email.dto.EmailTemplateName;
import com.amachi.app.core.management.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${mail.from:no-reply@example.com}") // configurable en application.yml
    private String defaultFrom;

    @Override
    @Async
    public void sendActivationEmail(
            String to,
            String username,
            EmailTemplateName templateName,
            String activationUrl,
            String activationCode,
            String subject,
            java.util.Map<String, Object> additionalContext) {
        try {
            String template = templateName != null ? templateName.getName() : "confirm-email";

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED,
                    StandardCharsets.UTF_8.name());

            java.util.Map<String, Object> props = new java.util.HashMap<>();
            props.put("username", username);
            props.put("activationUrl", activationUrl);
            props.put("activationCode", activationCode);
            
            if (additionalContext != null) {
                props.putAll(additionalContext);
            }

            Context context = new Context();
            context.setVariables(props);

            helper.setFrom(defaultFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(templateEngine.process(template, context), true);

            javaMailSender.send(mimeMessage);
            log.info("✅ Email de activación enviado a [{}]", to);

        } catch (MessagingException ex) {
            log.error("🚫 Error enviando email a [{}]: {}", to, ex.getMessage(), ex);
            throw new AppSecurityException(
                    ErrorCode.SEC_INVALID_OPERATION,
                    "security.email.send_failed",
                    to);
        }
    }

    @Override
    public void sendPasswordResetEmail(String email, String resetToken, String tenantCode) {

    }

    @Override
    public void sendPasswordResetEmail(String recipientEmail, String resetToken) {

    }
}
