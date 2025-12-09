package com.javarush.led.lesson16.model.note;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NoteIn {
    @Positive
    Long id;
    @Positive
    Long storyId;
    @Size(min = 2, max = 2048)
    String content;
}
