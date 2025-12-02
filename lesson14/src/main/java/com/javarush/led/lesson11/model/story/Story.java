package com.javarush.led.lesson11.model.story;

import com.javarush.led.lesson11.model.IdHolder;
import com.javarush.led.lesson11.model.editor.Editor;
import com.javarush.led.lesson11.model.note.Note;
import com.javarush.led.lesson11.model.tag.Tag;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Story implements IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Editor editor;

    @Column(unique = true)
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;

    @OneToMany(mappedBy = "story")
    final List<Note> notes = new ArrayList<>();
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "story_tag", // Имя связующей таблицы, как вы просили
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    final Set<Tag> tags = new HashSet<>();
}
