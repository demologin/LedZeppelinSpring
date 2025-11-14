package com.javarush.led.lesson09.service;

import com.javarush.led.lesson09.model.editor.EditorIn;
import com.javarush.led.lesson09.model.editor.EditorOut;
import com.javarush.led.lesson09.repository.EditorRepoImpl;
import com.javarush.led.lesson09.mapper.EditorDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EditorService {

    public final EditorRepoImpl repoImpl;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public final EditorDto mapper;

    public List<EditorOut> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public EditorOut get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public EditorOut create(EditorIn input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public EditorOut update(EditorIn input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
