package com.javarush.led.lesson16.repository;

import com.javarush.led.lesson16.model.story.Story;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepo extends Repo<Story> {
}
