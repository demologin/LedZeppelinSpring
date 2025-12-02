package com.javarush.led.lesson14.note.model;

import java.util.List;

public record NoteEvent(
        Operation operation,
        Long forById,
        NoteRequestTo noteRequestTo,
        List<NoteResponseTo> noteResponseTos
        ) {
    public NoteEvent(Operation operation) {
        this(operation, null, null, null);
    }

    public NoteEvent(Operation operation, Long forById) {
        this(operation, forById, null, null);
    }

    public NoteEvent(Operation operation, NoteRequestTo noteRequestTo) {
        this(operation, null, noteRequestTo, null);
    }

    public NoteEvent(List<NoteResponseTo> noteResponseTos) {
        this(null, null, null, noteResponseTos);
    }

    public enum Operation {
        FIND_ALL,
        FIND_BY_ID,
        CREATE,
        UPDATE,
        REMOVE_BY_ID,
    }

}
