package com.javarush.led.lesson11.service;

import com.javarush.led.lesson11.mapper.TagDto;
import com.javarush.led.lesson11.model.tag.TagIn;
import com.javarush.led.lesson11.model.tag.TagOut;
import com.javarush.led.lesson11.repository.TagRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TagService {

    public final TagRepo tagRepo;
    public final TagDto mapper;

    public Flux<TagOut> getAll() {
        return tagRepo
                .getAll()
                .map(mapper::out);
    }

    public Mono<TagOut> get(Long id) {
        return tagRepo
                .get(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Tag not found id=" + id)))
                .map(mapper::out);
    }

    public Mono<TagOut> create(TagIn input) {
        return Mono.just(input)
                .map(mapper::in)
                .flatMap(tagRepo::create)
                .map(mapper::out);
    }

    public Mono<TagOut> update(TagIn input) {
        return Mono.just(input)
                .map(mapper::in)
                .flatMap(tagRepo::update)
                .map(mapper::out);
    }

    public Mono<Void> delete(Long id) {
        return tagRepo.delete(id);
    }
}