package com.javarush.led.lesson11.service;

import com.javarush.led.lesson11.mapper.StoryDto;
import com.javarush.led.lesson11.model.editor.Editor;
import com.javarush.led.lesson11.model.story.Story;
import com.javarush.led.lesson11.model.story.StoryIn;
import com.javarush.led.lesson11.model.story.StoryOut;
import com.javarush.led.lesson11.model.tag.Tag;
import com.javarush.led.lesson11.repository.EditorRepo;
import com.javarush.led.lesson11.repository.StoryRepo;
import com.javarush.led.lesson11.repository.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    public final StoryRepo storyRepo;
    public final EditorRepo editorRepo;
    public final TagRepo tagRepo;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final StoryDto mapper;
    private final StoryDto storyDto;

    public List<StoryOut> getAll() {
        return storyRepo
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public StoryOut get(Long id) {
        return storyRepo
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public StoryOut create(StoryIn input) {
        Story story = createStory(input);
        return storyRepo
                .create(story)
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public StoryOut update(StoryIn input) {
        Story story = storyRepo.get(input.getId()).orElseThrow();
        storyDto.updateStoryFromDto(story, input);
        updateStoryTags(story, input);
        return mapper.out(story);
    }

    @Transactional
    public boolean delete(Long id) {
        Story story = storyRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Story not found id=" + id));

        Set<Long> tagIdsToDelete = story.getTags().stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        storyRepo.delete(story);

        if (!tagIdsToDelete.isEmpty()) {
            int deletedCount = tagRepo.deleteOrphanTags(tagIdsToDelete);
            System.out.println("Cleaned up " + deletedCount + " orphaned Tags after story deletion.");
        }
        return true;
    }

    private Story createStory(StoryIn input) {
        Story story = mapper.in(input);
        Optional<Editor> editor = editorRepo.get(input.getEditorId());
        story.setEditor(editor.orElseThrow());
        updateStoryTags(story, input);
        return story;
    }

    private void updateStoryTags(Story story, StoryIn input) {
        Set<Tag> newTags = input.getTags().stream()
                .map(name -> tagRepo.findByName(name)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(name);
                            return tagRepo.save(newTag);
                        }))
                .collect(Collectors.toSet());
        story.getTags().clear();
        story.getTags().addAll(newTags);
    }
}
