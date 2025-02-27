package com.cognito.volunteer_managment_system.core.security.dataAccess;

import com.cognito.volunteer_managment_system.core.security.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {
    Optional<Token> findByToken(String token);

    @Query("""
            select token
            from Token token
            where token.user.id=:userId
            and token.revoked=false
            and token.expired=false
            """)
    List<Token> findAllTokensByUser(Integer userId);
}
