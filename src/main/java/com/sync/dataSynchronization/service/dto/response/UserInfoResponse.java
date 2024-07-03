package com.sync.dataSynchronization.service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoResponse {

    private int userId;
    private String userHashCode;
    private String token;
}
