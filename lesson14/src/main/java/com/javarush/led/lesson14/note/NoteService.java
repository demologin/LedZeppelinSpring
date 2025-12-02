package com.javarush.led.lesson14.note;

import com.javarush.led.lesson14.note.model.NoteEvent;
import com.javarush.led.lesson14.note.model.NoteRequestTo;
import com.javarush.led.lesson14.note.model.NoteResponseTo;
import com.javarush.led.lesson11.repository.StoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService implements RestService<NoteRequestTo, NoteResponseTo> {

    public static final ParameterizedTypeReference<List<NoteResponseTo>> LIST_NOTE_RESPONSE_TO =
            new ParameterizedTypeReference<>() {
            };

    private final KafkaClient kafkaClient;

    private final StoryRepo storyRepository;

    @Override
    public List<NoteResponseTo> findAll() {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_ALL);
        NoteEvent result = kafkaClient.sync(noteEvent);
        return result.noteResponseTos();

    }

    @Override
    public NoteResponseTo findById(long noteId) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.FIND_BY_ID, noteId);
        NoteEvent result = kafkaClient.sync(noteEvent);
        return result.noteResponseTos().getFirst();
    }

    @Override
    public NoteResponseTo create(NoteRequestTo noteRequestTo) {
        Long storyId = noteRequestTo.storyId();
        if (storyRepository.existsById(storyId)) {
            NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.CREATE, noteRequestTo);
            NoteEvent result = kafkaClient.sync(noteEvent);
            return result.noteResponseTos().getFirst();
        } else {
            throw new IllegalStateException("incorrect storyId=" + storyId);
        }
    }

    @Override
    public NoteResponseTo update(NoteRequestTo noteRequestTo) {
        Long storyId = noteRequestTo.storyId();
        if (storyRepository.existsById(storyId)) {
            NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.UPDATE, noteRequestTo);
            NoteEvent result = kafkaClient.sync(noteEvent);
            return result.noteResponseTos().getFirst();
        } else {
            throw new IllegalStateException("incorrect storyId=" + storyId);
        }
    }

    @Override
    public boolean removeById(Long noteId) {
        NoteEvent noteEvent = new NoteEvent(NoteEvent.Operation.REMOVE_BY_ID, noteId);
        NoteEvent result = kafkaClient.sync(noteEvent);
        return result.noteResponseTos() == null || result.noteResponseTos().isEmpty();
    }

}