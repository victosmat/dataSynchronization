package com.sync.dataSynchronization.rest.errors;

import com.sync.dataSynchronization.config.ErrorCode;
import com.sync.dataSynchronization.rest.exceptions.BusinessException;
import com.sync.dataSynchronization.service.dto.ResponseData;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ResponseData<ObjectUtils.Null>> handleBadCredentialException(BadCredentialsException ex) {
        return new ResponseEntity<>(new ResponseData<>(ErrorCode.BAD_CREDENTIAL), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ResponseData<ObjectUtils.Null>> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new ResponseData<>(ex.getErrorCode()), HttpStatus.BAD_REQUEST);
    }

    @Nullable
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @Nullable Exception ex,
            @Nullable Object body,
            @Nullable HttpHeaders headers,
            @Nullable HttpStatusCode statusCode,
            @Nullable WebRequest request
    ) {
        if (ex instanceof MethodArgumentNotValidException fieldException) {
            String errorMessage = Objects.requireNonNull(fieldException.getBindingResult().getFieldError()).getDefaultMessage();
            return new ResponseEntity<>(new ResponseData<>(ErrorCode.INVALID_INPUT, errorMessage), HttpStatus.BAD_REQUEST);
        }

        if (ex instanceof MissingServletRequestParameterException missingServletRequestParameterException) {
            String paramName = missingServletRequestParameterException.getParameterName();
            String errorMessage = String.format("Missing mandatory param [%s]", paramName);
            return new ResponseEntity<>(new ResponseData<>(ErrorCode.INVALID_INPUT, errorMessage), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseData<>(ErrorCode.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }
}
