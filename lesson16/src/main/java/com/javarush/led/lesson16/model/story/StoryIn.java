package com.javarush.led.lesson16.model.story;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class StoryIn {
    @Positive
    Long id;

    @Positive
    Long editorId;

    @NotBlank
    @Size(min = 2, max = 64)
    String title;

    @Size(min = 4, max = 2048)
    String content;

    LocalDateTime created;
    LocalDateTime modified;

    Set<String> tags=new HashSet<>();

}
