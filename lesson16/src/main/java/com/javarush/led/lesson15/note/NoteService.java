package com.javarush.led.lesson15.note;

import com.javarush.led.lesson15.note.model.NoteEvent;
import com.javarush.led.lesson15.note.model.NoteRequestTo;
import com.javarush.led.lesson15.note.model.NoteResponseTo;
import com.javarush.led.lesson16.model.note.NoteIn;
import com.javarush.led.lesson16.model.note.NoteOut;
import com.javarush.led.lesson16.repository.StoryRepo;
import com.javarush.led.lesson16.mapper.NoteDto; // MapStruct Mapper
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final KafkaClient kafkaClient;
    private final StoryRepo storyRepository;
    private final NoteDto noteDtoMapper; // Внедрение MapStruct

    public Flux<NoteOut> getAll() {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_ALL);
        
        return kafkaClient.sync(noteEvent)
                .flatMapIterable(NoteEvent::noteResponseTos)
                .map(this::mapToNoteOut);
    }

    public Mono<NoteOut> get(long noteId) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_BY_ID, noteId);

        return kafkaClient.sync(noteEvent)
                .map(result -> result.noteResponseTos().getFirst())
                .map(this::mapToNoteOut)
                .onErrorMap(IndexOutOfBoundsException.class, e -> new NoSuchElementException("Note with id " + noteId + " not found"));
    }

    public Mono<NoteOut> create(NoteIn inputDto) {
        Long storyId = inputDto.getStoryId();

        // Изоляция блокирующего вызова репозитория
        return Mono.fromCallable(() -> storyRepository.existsById(storyId))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new IllegalStateException("incorrect storyId=" + storyId)))
                .flatMap(exists -> {
                    NoteRequestTo requestTo = this.mapToRequestTo(inputDto);
                    NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.CREATE, requestTo);

                    // KafkaClient.sync() уже неблокирующий и возвращает Mono
                    return kafkaClient.sync(noteEvent)
                            .map(result -> result.noteResponseTos().getFirst())
                            .map(this::mapToNoteOut);
                });
    }

    public Mono<NoteOut> update(NoteIn inputDto) {
        Long storyId = inputDto.getStoryId();

        // Изоляция блокирующего вызова репозитория
        return Mono.fromCallable(() -> storyRepository.existsById(storyId))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new IllegalStateException("incorrect storyId=" + storyId)))
                .flatMap(exists -> {
                    NoteRequestTo requestTo = this.mapToRequestTo(inputDto);
                    NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.UPDATE, requestTo);

                    // KafkaClient.sync() уже неблокирующий
                    return kafkaClient.sync(noteEvent)
                            .map(result -> result.noteResponseTos().getFirst())
                            .map(this::mapToNoteOut);
                })
                .onErrorMap(IndexOutOfBoundsException.class, e -> new NoSuchElementException("Note not found for update"));
    }

    public Mono<Void> delete(long noteId) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.REMOVE_BY_ID, noteId);

        // KafkaClient.sync() уже неблокирующий
        return kafkaClient.sync(noteEvent)
                .filter(result -> result.noteResponseTos() == null || result.noteResponseTos().isEmpty())
                .switchIfEmpty(Mono.error(new NoSuchElementException("Note with id " + noteId + " not found for deletion")))
                .then(); // Преобразует Mono<NoteEvent> в Mono<Void>
    }

    // --- DTO Mapping (MapStruct не покрывает NoteRequestTo/NoteResponseTo, используем прямые) ---

    // Преобразование DTO из контроллера в DTO для Kafka
    private NoteRequestTo mapToRequestTo(NoteIn inputDto) {
        return new NoteRequestTo(inputDto.getStoryId(), 
                                 inputDto.getId(),
                                 inputDto.getContent());
    }

    // Преобразование DTO из Kafka в DTO для контроллера
    private NoteOut mapToNoteOut(NoteResponseTo responseTo) {
        // Здесь MapStruct не может быть использован напрямую, так как NoteDto работает с Note.
        return new NoteOut(responseTo.id(), 
                           responseTo.storyId(), 
                           responseTo.content());
    }
}