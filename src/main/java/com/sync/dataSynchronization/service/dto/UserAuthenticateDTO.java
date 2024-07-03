package com.sync.dataSynchronization.service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserAuthenticateDTO {
    private Integer id;
    private String userHashCode;
}
