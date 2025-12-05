package com.javarush.led.lesson11.model.note;

import com.javarush.led.lesson11.model.IdHolder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_note")
public class Note implements IdHolder {

    @Id
    Long id;

    @Column("story_id")
    Long storyId;

    String content;


}
