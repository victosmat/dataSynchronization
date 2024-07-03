package com.sync.dataSynchronization.service.dto;

import com.sync.dataSynchronization.config.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@ToString
public class ResponseData<T> {

    private int code;

    @Getter
    private String message;

    @Setter
    private T data;

    public ResponseData(ErrorCode errorCode) {
        this.message = errorCode.getErrorMessage();
        this.code = Integer.parseInt(errorCode.getHttpStatus());
    }

    public ResponseData(ErrorCode errorCode, String message) {
        this.message = message;
        this.code = Integer.parseInt(errorCode.getHttpStatus());
    }

    public ResponseData(T data) {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = data;
    }

    public ResponseData() {
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.name();
        this.data = null;
    }
}
