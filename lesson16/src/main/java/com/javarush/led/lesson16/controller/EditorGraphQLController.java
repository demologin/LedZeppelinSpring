package com.javarush.led.lesson16.controller;

import com.javarush.led.lesson16.model.editor.EditorIn;
import com.javarush.led.lesson16.model.editor.EditorOut;
import com.javarush.led.lesson16.service.EditorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.NoSuchElementException;

@Controller
public class EditorGraphQLController {

    private final EditorService editorService;

    public EditorGraphQLController(EditorService editorService) {
        this.editorService = editorService;
    }

    // --- QUERIES (Чтение) ---

    // Соответствует Query: allEditors
    @QueryMapping
    public Flux<EditorOut> allEditors() {
        return editorService.getAll();
    }

    // Соответствует Query: editorById(id: ID!)
    @QueryMapping
    public Mono<EditorOut> editorById(@Argument Long id) {
        return editorService.get(id)
                // Обработка NoSuchElementException (не найдено) для соответствия 404 NOT_FOUND
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));
    }

    // --- MUTATIONS (Изменение) ---

    // Соответствует Mutation: createEditor(input: EditorInput!)
    @MutationMapping
    public Mono<EditorOut> createEditor(@Argument EditorIn input) {
        // Логика валидации (как @Valid в REST) обрабатывается Spring for GraphQL,
        // но вам может понадобиться дополнительная конфигурация или использование специальных валидаторов.
        return editorService.create(input);
    }

    // Соответствует Mutation: updateEditor(input: EditorInput!)
    // Примечание: В GraphQL-схеме мы сделали ID обязательным полем для Input при обновлении.
    @MutationMapping
    public Mono<EditorOut> updateEditor(@Argument EditorIn input) {
        return editorService.update(input)
                // Обработка NoSuchElementException (не найдено) при обновлении
                .onErrorMap(NoSuchElementException.class, e -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor to update not found"));
    }

    // Соответствует Mutation: deleteEditor(id: ID!)
    @MutationMapping
    public Mono<Boolean> deleteEditor(@Argument Long id) {
        // Мы вызываем Mono<Void> delete(id) из сервиса
        return editorService.delete(id)
                // Если Mono завершается успешно (onComplete), возвращаем true (успешно удалено)
                .thenReturn(true)
                // Если в процессе удаления возникла ошибка (напр., ID не найден, хотя в Mono<Void> это реже),
                // мы возвращаем false или выбрасываем исключение.
                // В вашем REST DELETE вы возвращали 404 при отсутствии, здесь сделаем это явно:
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found for deletion")))
                .onErrorResume(ResponseStatusException.class, e -> {
                    // Обрабатываем нашу 404 ошибку (которая будет отображена в errors GraphQL)
                    // или просто возвращаем false, если хотим скрыть детали
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                         // В GraphQL можно просто прокинуть ошибку, или если нужно вернуть false:
                         return Mono.error(e); // Propagate the error to GraphQL client
                    }
                    return Mono.error(e); 
                });
    }
}