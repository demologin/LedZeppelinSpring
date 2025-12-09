package com.javarush.led.lesson16.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
enum AppException {

    MethodArgumentNotValidException(HttpStatus.BAD_REQUEST, 1),
    IllegalStateException(HttpStatus.BAD_REQUEST, 2),
    InvalidDataAccessApiUsageException(HttpStatus.FORBIDDEN, 3),
    NoSuchElementException(HttpStatus.NOT_FOUND, 4),
    DataIntegrityViolationException(HttpStatus.FORBIDDEN, 5),
    NotFound(HttpStatus.NOT_FOUND, 6),
    BadRequest(HttpStatus.BAD_REQUEST, 7),
    ResponseStatusException(HttpStatus.BAD_REQUEST, 8),
    RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR, 99);

    private final HttpStatusCode httpStatus;
    private final int subCode;

    AppException(HttpStatusCode httpStatus, int subCode) {
        this.httpStatus = httpStatus;
        this.subCode = subCode;
    }

    int getErrorCode() {
        return httpStatus.value() * 100 + subCode;
    }

    public static AppException resolve(String simpleName) {
        try {
            return valueOf(simpleName);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
            return RuntimeException;
        }
    }

}
