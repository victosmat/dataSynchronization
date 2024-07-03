package com.sync.dataSynchronization.repository;

import com.sync.dataSynchronization.domain.UserAuthenticate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthenticateRepository extends JpaRepository<UserAuthenticate, Integer> {
    Optional<UserAuthenticate> findByUserHashCode(String lowercaseUserHashCode);
}
