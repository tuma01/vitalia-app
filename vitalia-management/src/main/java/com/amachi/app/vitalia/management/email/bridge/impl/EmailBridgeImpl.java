package com.amachi.app.vitalia.management.email.bridge.impl;

import com.amachi.app.core.auth.bridge.EmailBridge;
import com.amachi.app.vitalia.management.email.config.MailingProperties;
import com.amachi.app.vitalia.management.email.dto.EmailTemplateName;
import com.amachi.app.vitalia.management.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailBridgeImpl implements EmailBridge {

    private final EmailService emailService;
    private final MailingProperties mailingProperties;

    @Override
    public void sendActivationEmail(String to, String username, String activationCode) {

        String activationUrl = mailingProperties.getFrontend().getBaseUrl()
                + mailingProperties.getFrontend().getActivationPath()
                + "?token=" + activationCode;

        emailService.sendActivationEmail(
                to,
                username,
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                activationCode,
                "Account activation");
    }
}
