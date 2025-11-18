package com.javarush.led.lesson10.repository;

import com.javarush.led.lesson10.model.story.Story;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepo extends Repo<Story> {
}
