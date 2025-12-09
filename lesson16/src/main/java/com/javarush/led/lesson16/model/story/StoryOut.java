package com.javarush.led.lesson16.model.story;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoryOut {
    Long id;
    Long editorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;

    Set<String> tags=new HashSet<>();
}
