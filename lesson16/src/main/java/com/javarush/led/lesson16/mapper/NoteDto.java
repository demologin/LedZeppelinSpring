package com.javarush.led.lesson16.mapper;

import com.javarush.led.lesson16.model.note.Note;
import com.javarush.led.lesson16.model.note.NoteIn;
import com.javarush.led.lesson16.model.note.NoteOut;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NoteDto {

    NoteOut out(Note entity);

    Note in(NoteIn inputDto);
}
