package com.javarush.led.lesson10.model.editor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditorOut {
    Long id;
    String login;
    String firstname;
    String lastname;
}
