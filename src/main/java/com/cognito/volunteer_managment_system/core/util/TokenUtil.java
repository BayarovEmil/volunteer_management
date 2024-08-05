package com.cognito.volunteer_managment_system.core.util;

import com.cognito.volunteer_managment_system.core.security.dataAccess.TokenRepository;
import com.cognito.volunteer_managment_system.core.security.entity.Token;
import com.cognito.volunteer_managment_system.core.security.entity.TokenType;
import com.cognito.volunteer_managment_system.core.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    private final TokenRepository tokenRepository;
    public void revokeAllUsers(User user) {
        var validUserTokens = tokenRepository.findAllTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
    }

    public void saveUserToken(User user,String accessToken,String refreshToken) {
        var token = Token.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .expired(false)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepository.save(token);
    }
}
