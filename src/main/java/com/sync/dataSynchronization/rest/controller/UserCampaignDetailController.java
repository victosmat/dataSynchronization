package com.sync.dataSynchronization.rest.controller;

import com.sync.dataSynchronization.service.UserCampaignDetailService;
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
@RequestMapping("/user-campaign-detail")
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class UserCampaignDetailController {

    private final UserCampaignDetailService userCampaignDetailService;

    @PostMapping
    public ResponseEntity<ResponseData<UserInfoResponse>> createUserCampaignDetail(
            @Valid @RequestBody UserInfoRequest userInfoRequest
    ) {
        ResponseData<UserInfoResponse> response = userCampaignDetailService.createUserCampaignDetailInfo(userInfoRequest);
        log.info(
                """
                        POST
                        userInfoRequest: {}
                        Response API POST /user-campaign-detail: {}
                        """,
                userInfoRequest,
                response);
        return ResponseEntity.ok(response);
    }
}
