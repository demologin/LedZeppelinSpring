package com.javarush.led.lesson11.handler;

public record ValidationError(
        String object,
        String field,
        String message) {
}
