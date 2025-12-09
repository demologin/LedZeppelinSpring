package com.javarush.led.lesson16.handler;

public record ValidationError(
        String object,
        String field,
        String message) {
}
