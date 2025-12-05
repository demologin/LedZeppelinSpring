package com.javarush.led.lesson11.repository;

import com.javarush.led.lesson11.model.story.Story;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepo extends Repo<Story> {
}
