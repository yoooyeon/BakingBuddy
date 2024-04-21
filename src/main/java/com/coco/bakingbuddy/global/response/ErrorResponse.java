package com.coco.bakingbuddy.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private int code;
    private String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .code(status.value())
                        .message(message)
                        .build());
    }
}
