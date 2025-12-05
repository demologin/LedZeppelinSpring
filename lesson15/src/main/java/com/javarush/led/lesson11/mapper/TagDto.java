package com.javarush.led.lesson11.mapper;

import com.javarush.led.lesson11.model.tag.Tag;
import com.javarush.led.lesson11.model.tag.TagIn;
import com.javarush.led.lesson11.model.tag.TagOut;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TagDto {
    TagOut out(Tag entity);

    Tag in(TagIn inputDto);
}
