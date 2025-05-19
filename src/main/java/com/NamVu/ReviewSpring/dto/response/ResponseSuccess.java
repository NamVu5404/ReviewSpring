package com.NamVu.ReviewSpring.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseSuccess.Payload> {

    // PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatusCode status, String message) {
        super(Payload.builder()
                        .status(status.value())
                        .message(message)
                        .build(),
                HttpStatus.OK);
    }

    // GET, POST
    public ResponseSuccess(HttpStatusCode status, String message, Object data) {
        super(Payload.builder()
                        .status(status.value())
                        .message(message)
                        .data(data)
                        .build(),
                HttpStatus.OK);
    }

    @Builder
    public static class Payload {
        private final int status;
        private final String message;
        private Object data;
    }
}
