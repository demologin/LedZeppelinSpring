package com.javarush.led.lesson11.model.editor;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditorIn {
    @Positive
    Long id;
    @Size(min = 2, max = 64)
    String login;
    @Size(min = 8, max = 128)
    String password;
    @Size(min = 2, max = 64)
    String firstname;
    @Size(min = 2, max = 64)
    String lastname;
}
