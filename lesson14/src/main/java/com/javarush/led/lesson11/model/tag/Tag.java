package com.javarush.led.lesson11.model.tag;

import com.javarush.led.lesson11.model.IdHolder;
import com.javarush.led.lesson11.model.story.Story;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tag implements IdHolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Story> stories = new HashSet<>();

}
