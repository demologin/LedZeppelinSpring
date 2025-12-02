package com.javarush.led.lesson14.note.model;

public record NoteResponseTo(
        Long id,
        Long storyId,
        String content
) {
}
