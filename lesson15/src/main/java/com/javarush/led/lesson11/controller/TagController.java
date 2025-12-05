package com.javarush.led.lesson11.controller;

import com.javarush.led.lesson11.model.tag.TagIn;
import com.javarush.led.lesson11.model.tag.TagOut;
import com.javarush.led.lesson11.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public Flux<TagOut> getAll() {
        return tagService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TagOut> create(@RequestBody @Valid TagIn inputDto) {
        return tagService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<TagOut> update(@RequestBody @Valid TagIn inputDto) {
        return tagService.update(inputDto)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<TagOut> read(@PathVariable long id) {
        return tagService.get(id)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable long id) {
        return tagService.delete(id);
    }
}