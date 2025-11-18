package com.javarush.led.lesson10.repository;

import com.javarush.led.lesson10.model.editor.Editor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public interface EditorRepoImpl extends Repo<Editor> {

}
