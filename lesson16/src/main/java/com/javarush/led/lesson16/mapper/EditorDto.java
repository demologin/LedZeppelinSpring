package com.javarush.led.lesson16.mapper;

import com.javarush.led.lesson16.model.editor.Editor;
import com.javarush.led.lesson16.model.editor.EditorIn;
import com.javarush.led.lesson16.model.editor.EditorOut;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EditorDto {
    EditorOut out(Editor entity);

    Editor in(EditorIn inputDto);
}
