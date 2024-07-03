package com.sync.dataSynchronization.service.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVM {

    @NotNull(message = "username is mandatory")
    @Size(min = 1, max = 50, message = "username length is invalid")
    private String username;

    @NotNull(message = "password is mandatory")
    @Size(min = 4, max = 100, message = "password length is invalid")
    private String password;

    private boolean rememberMe;
}
