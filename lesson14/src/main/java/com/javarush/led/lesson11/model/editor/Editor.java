package com.javarush.led.lesson11.model.editor;

import com.javarush.led.lesson11.model.IdHolder;
import com.javarush.led.lesson11.model.story.Story;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Editor implements IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String login;
    String password;
    String firstname;
    String lastname;

    @OneToMany(mappedBy = "editor")
    final List<Story> stories = new ArrayList<>();
}
