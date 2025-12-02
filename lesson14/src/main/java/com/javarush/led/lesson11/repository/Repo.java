package com.javarush.led.lesson11.repository;

import com.javarush.led.lesson11.model.IdHolder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoRepositoryBean
public interface Repo<T extends IdHolder> extends CrudRepository<T, Long> {

    /**
     * Возвращает все сущности в виде потока.
     * Использует findAll() из CrudRepository.
     */
    default Stream<T> getAll() {
        return StreamSupport.stream(findAll().spliterator(), false);
    }

    /**
     * Находит сущность по ID.
     * Использует findById() из CrudRepository.
     */
    default Optional<T> get(Long id) {
        return findById(id);
    }

    /**
     * Создает (сохраняет) новую сущность.
     * Использует save() из CrudRepository.
     * Возвращает Optional<T> для соответствия исходному интерфейсу.
     */
    default Optional<T> create(T input) {
        Long id = input.getId();
        if (id!=null && existsById(id)) {
            return Optional.empty();
        }
        T saved = save(input);
        return Optional.of(saved);
    }

    /**
     * Обновляет существующую сущность.
     * Использует save() из CrudRepository.
     * В реальной жизни здесь требуется дополнительная логика проверки существования.
     */
    default Optional<T> update(T input) {
        if (existsById(input.getId())) {
            T updated = save(input);
            return Optional.of(updated);
        } else return Optional.empty();
    }

    /**
     * Удаляет сущность по ID.
     * Использует deleteById() из CrudRepository и возвращает true/false.
     * <p>
     * Внимание: CrudRepository не возвращает boolean.
     * Для соответствия исходному интерфейсу, мы предполагаем, что удаление
     * прошло успешно, если findById(id) после операции возвращает Optional.empty().
     */
    default boolean delete(Long id) {
        if (existsById(id)) {
            deleteById(id);
            return true;
        }
        // Проверка удаления (опционально, но рекомендуется для надежности):
        return false;
    }
}