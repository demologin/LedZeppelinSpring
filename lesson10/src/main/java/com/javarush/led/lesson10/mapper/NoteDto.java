package com.javarush.led.lesson10.mapper;

import com.javarush.led.lesson10.model.note.Note;
import com.javarush.led.lesson10.model.note.NoteIn;
import com.javarush.led.lesson10.model.note.NoteOut;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteDto {
    NoteOut out(Note entity);

    Note in(NoteIn inputDto);
}
