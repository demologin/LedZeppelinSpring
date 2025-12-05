package com.javarush.led.lesson11.model.tag;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagIn {
    @Positive
    Long id;

    @Size(min = 2, max = 32)
    String name;
}
