package com.cognito.volunteer_managment_system.core.util;

import com.cognito.volunteer_managment_system.core.email.EmailService;
import com.cognito.volunteer_managment_system.core.email.EmailTemplate;
import com.cognito.volunteer_managment_system.core.security.dataAccess.ActivationCodeRepository;
import com.cognito.volunteer_managment_system.core.security.entity.ActivationCode;
import com.cognito.volunteer_managment_system.core.security.entity.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final ActivationCodeRepository activationCodeRepository;
    private final EmailService emailService;
    public void sendValidationEmail(User user) throws MessagingException {
        var activationCode = generateAndSaveActivationCode(user);

        emailService.sendEmail(
                user.getUsername(),
                user.getEmail(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                activationCode,
                "Account activation"
        );
    }

    private String generateAndSaveActivationCode(User user) {
        var activationCode = generateActivationCode(6);

        var code = ActivationCode.builder()
                .code(activationCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(3))
                .user(user)
                .build();
        activationCodeRepository.save(code);
        return activationCode;
    }

    private String generateActivationCode(int length) {
        String characters = "1234567890";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i=0;i<length;i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
