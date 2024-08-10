package com.coco.bakingbuddy.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT(BAD_REQUEST, "유효하지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메서드 타입입니다."),
    ACCESS_DENIED(FORBIDDEN, "접근이 거부되었습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
    ENTITY_NOT_FOUND(NOT_FOUND, "Entity를 찾을 수 없습니다."),
    NOT_AUTHORIZED(BAD_REQUEST, "권한이 없습니다."),

    // user
    USER_NOT_FOUND(NOT_FOUND, "존재하지 않는 유저"),
    DUPLICATE_EMAIL(BAD_REQUEST, "중복된 이메일이 존재합니다."),
    DUPLICATE_USERNAME(BAD_REQUEST, "이미 가입된 회원입니다. 다른 사용자명으로 시도해주세요."),
    INVALID_PASSWORD(BAD_REQUEST, "패스워드가 일치하지 않습니다."),

    //dir
    DIRECTORY_NOT_FOUND(NOT_FOUND, "존재하지 않는 디렉토리"),
    DUPLICATE_DIRECTORY(BAD_REQUEST, "중복된 디렉토리명이 존재합니다."),

    //recipe
    RECIPE_NOT_FOUND(NOT_FOUND, "존재하지 않는 레시피"),

    // auth
    AUTH_NOT_FOUND(NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_AUTH(BAD_REQUEST, "인증 정보가 잘못되었습니다."),
    LOGIN_FAILED(BAD_REQUEST, "비밀번호가 맞지 않습니다."),

    // recipeStep
    RECIPE_STEP_NOT_FOUND(NOT_FOUND, "레시피 조리 단계를 찾을 수 없습니다."),
    USERNAME_NOT_FOUND(NOT_FOUND, "가입되지 않은 USERNAME 입니다."),

    // jwt
    JWT_INVALID(BAD_REQUEST, "정보가 맞지 않습니다."),
    JWT_EXPIRED(BAD_REQUEST, "정보가 맞지 않습니다."),
    JWT_UNSUPPORTED(BAD_REQUEST, "정보가 맞지 않습니다."),
    JWT_INVALID_SIGNATURE(BAD_REQUEST, "정보가 맞지 않습니다."),
    JWT_NO_VALID(BAD_REQUEST, "정보가 맞지 않습니다."),

    UNAUTHORIZED_DELETE(BAD_REQUEST, "삭제 권한이 없습니다."),

    // file
    MAX_UPLOAD_SIZE_EXCEEDED(BAD_REQUEST, "파일 최대 용량을 초과하였습니다."),
    UUID_NOT_FOUND(BAD_REQUEST, "UUID가 존재하지 않습니다."),

    // follow
    CANNOT_FOLLOW_SELF(BAD_REQUEST, "자기 자신을 팔로우 할 수 없습니다."),

    //auth
    INVALID_CREDENTIALS(BAD_REQUEST, "로그인 정보가 맞지 않습니다."),

    // product
    PRODUCT_NOT_FOUND(BAD_REQUEST,"상품 정보를 찾을 수 없습니다"),

    ;


    private final HttpStatus status;
    private final String message;
}