package com.sync.dataSynchronization.service;

import com.sync.dataSynchronization.rest.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.sync.dataSynchronization.security.SecurityUtils.AUTHORITIES_KEY;
import static com.sync.dataSynchronization.security.SecurityUtils.JWT_ALGORITHM;

import com.sync.dataSynchronization.config.ErrorCode;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommonService {

    private final JwtEncoder jwtEncoder;

    @Value("${jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    public String createToken(Authentication authentication, boolean rememberMe, Long userAuthenticationID) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity;
        if (rememberMe) {
            validity = now.plus(this.tokenValidityInSecondsForRememberMe, ChronoUnit.SECONDS);
        } else {
            validity = now.plus(this.tokenValidityInSeconds, ChronoUnit.SECONDS);
        }

        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim("userAuthenticationID", userAuthenticationID)
                .claim(AUTHORITIES_KEY, authorities)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public Optional<Long> getUserAuthenticationIDFromJwt() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        try {
            Map<String, Object> claims = ((JwtAuthenticationToken) authentication).getToken().getClaims();
            Object userAuthenticationID = claims.get("userAuthenticationID");
            return Optional.of((Long) userAuthenticationID);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.UNABLE_TO_GET_USER_AUTHENTICATION_ID_FROM_JWT);
        }
    }
}
