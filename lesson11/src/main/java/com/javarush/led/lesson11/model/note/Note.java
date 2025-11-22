package com.javarush.led.lesson11.model.note;

import com.javarush.led.lesson11.model.IdHolder;
import com.javarush.led.lesson11.model.story.Story;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Note implements IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Story story;

    String content;


}
