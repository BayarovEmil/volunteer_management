package com.cognito.volunteer_managment_system.core.security.dataAccess;

import com.cognito.volunteer_managment_system.core.security.entity.ActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode,Integer> {
    Optional<ActivationCode> findByCode(String code);
}
