package com.NamVu.ReviewSpring.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class ErrorResponse {

    private Date timestamp;
    private int status;
    private String path;
    private String error;
    private String message;
}
