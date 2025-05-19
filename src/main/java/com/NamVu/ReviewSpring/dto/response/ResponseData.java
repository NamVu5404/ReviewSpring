package com.NamVu.ReviewSpring.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private final int status;
    private final String message;
    private T data;
}
