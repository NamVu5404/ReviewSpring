package com.NamVu.ReviewSpring.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,
                                                                HttpServletRequest request) {
        String errorMessage = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        log.error("error 400: {}", errorMessage);

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .message(errorMessage)
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse exceptionHandler(Exception e, HttpServletRequest request) {
        log.error("error 500: {}", e.getMessage());

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .message("Uncategorized Exception")
                .build();
    }

    @ExceptionHandler(AppException.class)
    public ErrorResponse appExceptionHandler(AppException e, HttpServletRequest request) {
        String errorMessage = e.getErrorCode().getMessage();
        log.error("app exception: {}", errorMessage);

        return ErrorResponse.builder()
                .timestamp(new Date())
                .status(e.getErrorCode().getHttpStatus().value())
                .error(e.getErrorCode().getHttpStatus().getReasonPhrase())
                .path(request.getRequestURI())
                .message(errorMessage)
                .build();
    }
}
