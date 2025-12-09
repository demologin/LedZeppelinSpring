package com.javarush.led.lesson16.mapper;

import com.javarush.led.lesson16.model.story.Story;
import com.javarush.led.lesson16.model.story.StoryIn;
import com.javarush.led.lesson16.model.story.StoryOut;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StoryDto {

    StoryOut out(Story entity);

    Story in(StoryIn inputDto);

}
