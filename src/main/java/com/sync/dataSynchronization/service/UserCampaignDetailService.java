package com.sync.dataSynchronization.service;

import com.sync.dataSynchronization.config.Constants;
import com.sync.dataSynchronization.config.UserSessionManager;
import com.sync.dataSynchronization.domain.UserAuthenticate;
import com.sync.dataSynchronization.repository.UserAuthenticateRepository;
import com.sync.dataSynchronization.rest.exceptions.BusinessException;
import com.sync.dataSynchronization.service.dto.ResponseData;
import com.sync.dataSynchronization.service.dto.UserAuthenticateDTO;
import com.sync.dataSynchronization.service.dto.request.UserInfoRequest;
import com.sync.dataSynchronization.service.dto.response.UserInfoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;
import com.sync.dataSynchronization.config.ErrorCode;

import java.util.Date;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserCampaignDetailService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserAuthenticateRepository userAuthenticateRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommonService commonService;
    private final UserSessionManager userSessionManager;

    @Transactional(rollbackOn = Exception.class)
    public ResponseData<UserInfoResponse> createUserCampaignDetailInfo(UserInfoRequest userInfoRequest) {
        String userHashCode = UriUtils.decode(userInfoRequest.getUserHashCode(), Constants.UTF_8);
        String password = userInfoRequest.getPassword();

        UserAuthenticate userAuthenticate = userAuthenticateRepository.findByUserHashCode(userHashCode).orElse(null);
        if (Objects.nonNull(userAuthenticate)) throw new BusinessException(ErrorCode.USER_AUTHENTICATION_EXIST);

        try {
            userAuthenticate = userAuthenticateRepository.save(
                    UserAuthenticate
                            .builder()
                            .userHashCode(userHashCode)
                            .password(passwordEncoder.encode(password))
                            .createdDate(new Date())
                            .build());
        } catch (DataAccessException e) {
            throw new BusinessException(ErrorCode.UNABLE_TO_CREATE_USER_AUTHENTICATION);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userHashCode.toLowerCase(),
                password
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = commonService.createToken(authentication, false, Long.valueOf(userAuthenticate.getId()));

        userSessionManager.createSession(
                UserAuthenticateDTO
                        .builder()
                        .id(userAuthenticate.getId())
                        .userHashCode(userAuthenticate.getUserHashCode())
                        .build());

        return new ResponseData<>(UserInfoResponse
                .builder()
                .userId(userAuthenticate.getId())
                .userHashCode(userHashCode)
                .token(token)
                .build());
    }
}
