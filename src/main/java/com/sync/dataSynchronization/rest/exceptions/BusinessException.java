package com.sync.dataSynchronization.rest.exceptions;

import com.sync.dataSynchronization.config.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        errorCode.updateErrorMessage(message);
        this.errorCode = errorCode;
    }
}
