package com.javarush.led.lesson16.service;

import com.javarush.led.lesson16.mapper.StoryDto;
import com.javarush.led.lesson16.model.story.Story;
import com.javarush.led.lesson16.model.story.StoryIn;
import com.javarush.led.lesson16.model.story.StoryOut;
import com.javarush.led.lesson16.model.tag.Tag;
import com.javarush.led.lesson16.repository.EditorRepo;
import com.javarush.led.lesson16.repository.StoryRepo;
import com.javarush.led.lesson16.repository.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

    public final StoryRepo storyRepo;
    public final EditorRepo editorRepo;
    public final TagRepo tagRepo;
    public final StoryDto mapper;
    private final StoryDto storyDto;

    public Flux<StoryOut> getAll() {
        return storyRepo
                .getAll()
                .map(mapper::out)
                ;
    }

    public Mono<StoryOut> get(Long id) {
        return storyRepo
                .get(id)
                .map(mapper::out)
                ;
    }

    @Transactional
    public Mono<StoryOut> create(StoryIn input) {
        return createStory(input)
                .flatMap(storyRepo::create)
                .map(mapper::out);
    }

    @Transactional
    public Mono<StoryOut> update(StoryIn input) {
        return storyRepo.get(input.getId())
                .switchIfEmpty(Mono.error(new NoSuchElementException("Story not found id=" + input.getId())))
                .flatMap(story -> {
//                    storyDto.updateStoryFromDto(story, input);
                    return updateStoryTags(story, input);
                })
                .flatMap(storyRepo::update)
                .map(mapper::out);
    }

    @Transactional
    public Mono<Boolean> delete(Long id) {
        return storyRepo.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Story not found id=" + id)))
                .flatMap(story -> {
//                    Set<Long> tagIdsToDelete = story.getTags().stream()
//                            .map(Tag::getId)
//                            .collect(Collectors.toSet());

                    return storyRepo.delete(story)
                            .then(Mono.defer(() -> {
                                return Mono.just(true);
                            }));
                });
    }

    private Mono<Story> createStory(StoryIn input) {
        Story story = mapper.in(input);

        return editorRepo.get(input.getEditorId())
                .switchIfEmpty(Mono.error(new NoSuchElementException("Editor not found id=" + input.getEditorId())))
                .flatMap(editor -> {
                    story.setEditorId(editor.getId());
                    return updateStoryTags(story, input);
                });
    }

    private Mono<Story> updateStoryTags(Story story, StoryIn input) {
        Set<String> tagNames = input.getTags();

        Flux<Tag> tagFlux = Flux.fromIterable(tagNames)
                .flatMap(name -> tagRepo.findByName(name)
                        .switchIfEmpty(Mono.defer(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(name);
                            return tagRepo.save(newTag);
                        }))
                );

        return tagFlux.collect(Collectors.toSet())
                .map(newTags -> {
//                    story.getTags().clear();
//                    story.getTags().addAll(newTags);
                    return story;
                });
    }
}