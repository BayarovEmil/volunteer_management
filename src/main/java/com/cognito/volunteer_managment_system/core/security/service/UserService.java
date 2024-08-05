package com.cognito.volunteer_managment_system.core.security.service;

import com.cognito.volunteer_managment_system.core.security.dataAccess.ActivationCodeRepository;
import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.dto.ChangePassword;
import com.cognito.volunteer_managment_system.core.security.entity.User;
import com.cognito.volunteer_managment_system.core.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ActivationCodeRepository activationCodeRepository;
    private final EmailUtil emailSenderUtil;
    public void changePassword(ChangePassword changePassword, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();

        if (!passwordEncoder.matches(changePassword.currentPassword(), user.getPassword())) {
            throw new IllegalStateException("Current password is wrong");
        }

        if (!Objects.equals(changePassword.newPassword(),changePassword.confirmationPassword())) {
            throw new IllegalStateException("This passwords are not the same!");
        }

        user.setPassword(passwordEncoder.encode(changePassword.newPassword()));
        userRepository.save(user);
    }

    public void forgetPassword(Authentication connectedUser) throws MessagingException {
        User user = (User) connectedUser.getPrincipal();
        emailSenderUtil.sendValidationEmail(user);
    }

    public void resetPassword(Authentication connectedUser,String myCode, String newPassword,String confirmationPassword) throws MessagingException {
        User user = (User) connectedUser.getPrincipal();
        var code = activationCodeRepository.findByCode(myCode)
                .orElseThrow(()->new EntityNotFoundException("Code not found"));
        if (!Objects.equals(code.getUser().getId(),user.getId())) {
            throw new IllegalStateException("Wrong password!");
        }

        if (LocalDateTime.now().isAfter(code.getExpiresAt())) {
            emailSenderUtil.sendValidationEmail(user);
            throw new IllegalStateException("This code has been expired");
        }

        if (!(newPassword.equals(confirmationPassword))) {
            throw new IllegalStateException("This passwords are not same!");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deactivateAccount(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        user.setEnabled(false);
        user.setAccountLocked(true);
        userRepository.save(user);
    }
}
