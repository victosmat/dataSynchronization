package com.sync.dataSynchronization.rest.controller;

import com.sync.dataSynchronization.service.UserAuthenticationService;
import com.sync.dataSynchronization.service.dto.ResponseData;
import com.sync.dataSynchronization.service.dto.request.UserInfoRequest;
import com.sync.dataSynchronization.service.dto.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-authentication")
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class UserAuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping
    public ResponseEntity<ResponseData<UserInfoResponse>> createUserAuthentication(@Valid @RequestBody UserInfoRequest userInfoRequest) {
        ResponseData<UserInfoResponse> response = userAuthenticationService.createUserAuthentication(userInfoRequest);
        log.info(
                """
                        POST
                        userInfoRequest: {}
                        Response API POST /user-authentication: {}
                        """,
                userInfoRequest,
                response);
        return ResponseEntity.ok(response);
    }
}
