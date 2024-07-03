package com.sync.dataSynchronization.security;

import com.sync.dataSynchronization.config.ErrorCode;
import com.sync.dataSynchronization.domain.UserAuthenticate;
import com.sync.dataSynchronization.repository.UserAuthenticateRepository;
import com.sync.dataSynchronization.rest.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Locale;

@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserAuthenticateRepository userAuthenticateRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String userHashCode) {
        log.debug("Authenticating {}", userHashCode);
        String lowercaseUserHashCode = userHashCode.toLowerCase(Locale.ENGLISH);
        return userAuthenticateRepository
            .findByUserHashCode(lowercaseUserHashCode)
            .map(this::createSpringSecurityUser)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_AUTHENTICATION_NOT_FOUND));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(UserAuthenticate userAuthenticate) {
        return new org.springframework.security.core.userdetails.User(
            userAuthenticate.getUserHashCode(),
            userAuthenticate.getPassword(),
            Collections.emptyList()
        );
    }
}
