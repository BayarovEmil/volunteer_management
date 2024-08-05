package com.cognito.volunteer_managment_system.core.security.service;

import com.cognito.volunteer_managment_system.core.security.config.JwtService;
import com.cognito.volunteer_managment_system.core.security.dataAccess.ActivationCodeRepository;
import com.cognito.volunteer_managment_system.core.security.dataAccess.UserRepository;
import com.cognito.volunteer_managment_system.core.security.dto.AuthenticationRequest;
import com.cognito.volunteer_managment_system.core.security.dto.AuthenticationResponse;
import com.cognito.volunteer_managment_system.core.security.dto.RegistrationRequest;
import com.cognito.volunteer_managment_system.core.security.entity.Role;
import com.cognito.volunteer_managment_system.core.security.entity.User;
import com.cognito.volunteer_managment_system.core.util.EmailUtil;
import com.cognito.volunteer_managment_system.core.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ActivationCodeRepository activationCodeRepository;
    private final TokenUtil tokenUtil;
    private final EmailUtil emailUtil;
    public void register(RegistrationRequest request) throws MessagingException {
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .accountLocked(false)
                .enabled(false)
                .build();
        userRepository.save(user);
        emailUtil.sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("getName",user.getName());

        String accessToken = jwtService.generateToken(claims,user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenUtil.revokeAllUsers(user);
        tokenUtil.saveUserToken(user,accessToken,refreshToken);
        return AuthenticationResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    public void activateAccount(String code) throws MessagingException {
        var activationCode = activationCodeRepository.findByCode(code)
                .orElseThrow(()->new EntityNotFoundException("Activation code not found"));
        if (LocalDateTime.now().isAfter(activationCode.getExpiresAt())) {
            emailUtil.sendValidationEmail(activationCode.getUser());
            throw new IllegalStateException("Activation code has been expired");
        }

        User user = userRepository.findByEmail(activationCode.getUser().getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found by email"));
        user.setEnabled(true);
        activationCode.setLastModifiedAt(LocalDateTime.now());

        userRepository.save(user);
        activationCodeRepository.save(activationCode);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String userEmail;
        final String refreshToken;
        if (authHeader==null || !authHeader.startsWith("Bearer")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail!=null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(()->new UsernameNotFoundException("User not found by email"));
            if (jwtService.isTokenValid(refreshToken,user)) {
                var accessToken = jwtService.generateToken(user);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
