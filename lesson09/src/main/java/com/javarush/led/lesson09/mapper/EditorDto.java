package com.javarush.led.lesson09.mapper;

import com.javarush.led.lesson09.model.editor.Editor;
import com.javarush.led.lesson09.model.editor.EditorIn;
import com.javarush.led.lesson09.model.editor.EditorOut;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorDto {
    EditorOut out(Editor entity);

    Editor in(EditorIn inputDto);
}
