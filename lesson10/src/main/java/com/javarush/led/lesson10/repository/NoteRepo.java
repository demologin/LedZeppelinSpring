package com.javarush.led.lesson10.repository;

import com.javarush.led.lesson10.model.note.Note;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepo extends Repo<Note> {
}
