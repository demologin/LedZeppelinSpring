package com.javarush.led.lesson15.note;

import com.javarush.led.lesson11.model.note.NoteIn;
import com.javarush.led.lesson11.model.note.NoteOut;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public Flux<NoteOut> getAll() {
        return noteService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NoteOut> create(@RequestBody @Valid NoteIn inputDto) {
        return noteService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<NoteOut> update(@RequestBody @Valid NoteIn inputDto) {
        return noteService.update(inputDto)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<NoteOut> read(@PathVariable long id) {
        return noteService.get(id)
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable long id) {
        return noteService.delete(id);
    }
}