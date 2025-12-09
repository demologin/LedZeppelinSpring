package com.javarush.led.lesson16.model.tag;

import com.javarush.led.lesson16.model.IdHolder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tbl_tag")
public class Tag implements IdHolder {

    @Id
    Long id;
    String name;

}
