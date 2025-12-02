package com.javarush.led.lesson11.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @see <a href=https://datatracker.ietf.org/doc/html/rfc7807>RFC7807</a>
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException ex) {
        var errorData = AppException.resolve(ex.getClass().getSimpleName());
        return ErrorResponse
                .builder(ex, errorData.getHttpStatus(), errorData.name())
                .title(ex.getMessage())
                .property("errorCode", errorData.getErrorCode())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        var fieldErrors = getFieldErrors(bindingResult);
        var errorData = AppException.resolve(ex.getClass().getSimpleName());
        return ErrorResponse
                .builder(ex, errorData.getHttpStatus(), ex.getMessage())
                .title(fieldErrors.size() + " validation error(s) found.")
                .property("fieldErrors", fieldErrors)
                .property("errorCode", errorData.getErrorCode())
                .build();
    }

    private static List<ValidationError> getFieldErrors(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getDefaultMessage()))
                .toList();
    }
}
