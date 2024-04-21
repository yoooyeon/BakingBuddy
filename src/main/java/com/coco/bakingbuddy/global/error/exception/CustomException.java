package com.coco.bakingbuddy.global.error.exception;

import com.coco.bakingbuddy.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode code;

    public CustomException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public CustomException(ErrorCode code) {
        this.code = code;
    }
}