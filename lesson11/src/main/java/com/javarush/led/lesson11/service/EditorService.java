package com.javarush.led.lesson11.service;

import com.javarush.led.lesson11.mapper.EditorDto;
import com.javarush.led.lesson11.model.editor.EditorIn;
import com.javarush.led.lesson11.model.editor.EditorOut;
import com.javarush.led.lesson11.repository.EditorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EditorService {

    public final EditorRepo editorRepo;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final EditorDto mapper;

    public List<EditorOut> getAll() {
        return editorRepo
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public EditorOut get(Long id) {
        return editorRepo
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public EditorOut create(EditorIn input) {
        return editorRepo
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public EditorOut update(EditorIn input) {
        return editorRepo
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return editorRepo.delete(id);
    }
}
