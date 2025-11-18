package com.javarush.led.lesson10.handler;

public record ValidationError(
        String object,
        String field,
        String message) {
}
