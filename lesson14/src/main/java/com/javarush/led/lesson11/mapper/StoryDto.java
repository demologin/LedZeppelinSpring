package com.javarush.led.lesson11.mapper;

import com.javarush.led.lesson11.model.story.Story;
import com.javarush.led.lesson11.model.story.StoryIn;
import com.javarush.led.lesson11.model.story.StoryOut;
import com.javarush.led.lesson11.model.tag.Tag;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StoryDto {

    @Mapping(source = "editor.id", target = "editorId")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapTagsToStrings")
    StoryOut out(Story entity);

    Story in(StoryIn inputDto);

    @Named("mapTagsToStrings")
    default Set<String> mapTagsToStrings(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toSet());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "editor", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void updateStoryFromDto(@MappingTarget Story entity, StoryIn inputDto);
}
