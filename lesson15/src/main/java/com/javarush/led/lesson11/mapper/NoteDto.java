package com.javarush.led.lesson11.mapper;

import com.javarush.led.lesson11.model.note.Note;
import com.javarush.led.lesson11.model.note.NoteIn;
import com.javarush.led.lesson11.model.note.NoteOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NoteDto {

    NoteOut out(Note entity);

    Note in(NoteIn inputDto);
}
