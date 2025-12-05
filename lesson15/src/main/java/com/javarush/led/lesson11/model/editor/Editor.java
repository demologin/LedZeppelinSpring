package com.javarush.led.lesson11.model.editor;

import com.javarush.led.lesson11.model.IdHolder;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id; // <-- Используем R2DBC Id
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_editor")
public class Editor implements IdHolder {

    @Id
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;
}
