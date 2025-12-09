package com.javarush.led.lesson16.controller;

import com.javarush.led.lesson16.model.story.StoryIn;
import com.javarush.led.lesson16.model.story.StoryOut;
import com.javarush.led.lesson16.service.StoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/stories")
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping
    public Flux<StoryOut> getAll() {
        return storyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<StoryOut> create(@RequestBody @Valid StoryIn inputDto) {
        return storyService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<StoryOut> update(@RequestBody @Valid StoryIn inputDto) {
        return storyService.update(inputDto)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<StoryOut> read(@PathVariable long id) {
        return storyService.get(id)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable long id) {
        return storyService.delete(id)
                .filter(result -> result)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .then();
    }
}