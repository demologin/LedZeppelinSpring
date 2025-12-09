package com.javarush.led.lesson16.mapper;

import com.javarush.led.lesson16.model.tag.Tag;
import com.javarush.led.lesson16.model.tag.TagIn;
import com.javarush.led.lesson16.model.tag.TagOut;
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
