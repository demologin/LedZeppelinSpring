package com.javarush.led.lesson16.controller;

import com.javarush.led.lesson16.model.editor.EditorIn;
import com.javarush.led.lesson16.model.editor.EditorOut;
import com.javarush.led.lesson16.service.EditorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/editors")
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping
    public Flux<EditorOut> getAll() {
        Flux<EditorOut> all = editorService.getAll();
        return all;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EditorOut> create(@RequestBody @Valid EditorIn inputDto) {
        return editorService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<EditorOut> update(@RequestBody @Valid EditorIn inputDto) {
        return editorService.update(inputDto)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<EditorOut> read(@PathVariable long id) {
        return editorService.get(id)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable long id) {
        return editorService.delete(id);
    }
}