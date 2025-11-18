package com.javarush.led.lesson10.repository;

import com.javarush.led.lesson10.model.tag.Tag;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends Repo<Tag> {
}
