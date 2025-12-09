package com.javarush.led.lesson16.service;

import com.javarush.led.lesson16.mapper.EditorDto;
import com.javarush.led.lesson16.model.editor.EditorIn;
import com.javarush.led.lesson16.model.editor.EditorOut;
import com.javarush.led.lesson16.repository.EditorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EditorService {

    public final EditorRepo editorRepo;

    public final EditorDto mapper;

    public Flux<EditorOut> getAll() {
        return editorRepo
                .getAll()
                .map(mapper::out);
    }

    public Mono<EditorOut> get(Long id) {
        return editorRepo
                .get(id)
                .map(mapper::out);
    }

    @Transactional
    public Mono<EditorOut> create(EditorIn input) {
        return editorRepo
                .create(mapper.in(input))
                .map(mapper::out)
                ;
    }

    @Transactional
    public Mono<EditorOut> update(EditorIn input) {
        return editorRepo
                .update(mapper.in(input))
                .map(mapper::out)
                ;
    }

    @Transactional
    public Mono<Void> delete(Long id) {
        return editorRepo.delete(id);
    }
}
