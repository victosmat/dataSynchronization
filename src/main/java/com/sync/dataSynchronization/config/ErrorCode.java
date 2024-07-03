package com.sync.dataSynchronization.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public enum ErrorCode {
    BAD_CREDENTIAL(HttpStatus.UNAUTHORIZED, "Username or password is not correct"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input field"),
    USER_AUTHENTICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "User authentication not found"),
    USER_AUTHENTICATION_EXIST(HttpStatus.BAD_REQUEST, "User authentication exist"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    USER_EXIST_IN_SESSION(HttpStatus.BAD_REQUEST, "User exist in session"),
    UNABLE_TO_CREATE_USER_AUTHENTICATION(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to create user authentication"),
    UNABLE_TO_GET_USER_AUTHENTICATION_ID_FROM_JWT(HttpStatus.BAD_REQUEST, "Unable to get user authentication id from jwt");

    private String httpStatus;
    private String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = String.valueOf(httpStatus.value());
        this.errorMessage = errorMessage;
    }

    public void updateErrorMessage(String newErrorMessage) {
        this.errorMessage = newErrorMessage;
    }

}
