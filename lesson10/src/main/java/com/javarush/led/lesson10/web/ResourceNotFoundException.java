package com.javarush.led.lesson10.web;

/**
 * Простейшее доменное исключение для случаев, когда ресурс не найден.
 * Бросайте его из сервисов/контроллеров, а GlobalExceptionHandler вернёт 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}