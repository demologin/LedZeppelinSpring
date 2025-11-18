package com.javarush.led.lesson10.service;

import com.javarush.led.lesson10.api.TestEnv;
import com.javarush.led.lesson10.model.editor.Editor;
import com.javarush.led.lesson10.model.editor.EditorIn;
import com.javarush.led.lesson10.model.editor.EditorOut;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class EditorServiceIT extends TestEnv {

    private final EditorService editorService;

    private final Long TEST_ID = 11L;

    private EditorOut mockOut;
    private EditorIn mockIn;

    @BeforeEach
    void setUp() {

        mockOut = EditorOut.builder()
                .login("test_user")
                .firstname("Test")
                .lastname("User")
                .build();

        mockIn = EditorIn.builder()
                .login("new_user")
                .password("new_secure_pass123")
                .firstname("New")
                .lastname("User")
                .build();
    }

    // --- Параметрический тест для CREATE ---

    private static Stream<Arguments> creationTestData() {
        // Scenario 1: New Editor
        EditorIn input1 = EditorIn.builder()
                .login("john.doe")
                .password("strongPassword!1")
                .firstname("John")
                .lastname("Doe")
                .build();
        Editor entity1 = Editor.builder()
                .id(10L)
                .login("john.doe")
                .password("strongPassword!1")
                .firstname("John")
                .lastname("Doe")
                .build();
        EditorOut output1 = EditorOut.builder()
                .id(10L)
                .login("john.doe")
                .firstname("John")
                .lastname("Doe")
                .build();

        // Scenario 2: Another New Editor
        EditorIn input2 = EditorIn.builder()
                .login("alice_w")
                .password("AlicePass321")
                .firstname("Alice")
                .lastname("Wonder")
                .build();
        Editor entity2 = Editor.builder()
                .id(11L)
                .login("alice_w")
                .password("AlicePass321")
                .firstname("Alice")
                .lastname("Wonder")
                .build();
        EditorOut output2 = EditorOut.builder()
                .id(11L)
                .login("alice_w")
                .firstname("Alice")
                .lastname("Wonder")
                .build();

        return Stream.of(
                Arguments.of(input1, entity1, output1),
                Arguments.of(input2, entity2, output2)
        );
    }

    @ParameterizedTest(name = "Create with login: {0}")
    @MethodSource("creationTestData")
    void createShouldReturnEditorOutOnSuccessParameterized(EditorIn input, Editor entity, EditorOut expectedOut) {


        EditorOut actual = editorService.create(input);

        assertNotNull(actual);
        assertEquals(expectedOut.getLogin(), actual.getLogin());
        assertEquals(expectedOut.getFirstname(), actual.getFirstname());
        assertEquals(expectedOut.getLastname(), actual.getLastname());

    }

    @Test
    void getShouldReturnEditorOutForValidId() {
        EditorOut editorOut = editorService.create(mockIn);
        EditorOut actual = editorService.get(editorOut.getId());
        assertNotNull(actual);
    }

    // --- Тест для UPDATE (используем mockIn/mockEntity/mockOut из setup) ---

    @Test
    void updateShouldReturnEditorOutOnSuccess() {
        EditorOut actual = editorService.update(mockIn);

        assertNotNull(actual);
        assertEquals(mockIn.getLogin(), actual.getLogin());
        assertEquals(mockIn.getFirstname(), actual.getFirstname());
        assertEquals(mockIn.getLastname(), actual.getLastname());

    }

    // --- Другие тесты ---

    @Test
    void getAllShouldReturnListOfEditorOut() {

        List<EditorOut> actual = editorService.getAll();
        assertNotNull(actual);

    }

    @Test
    void getShouldThrowExceptionForInvalidId() {

        assertThrows(java.util.NoSuchElementException.class, () -> editorService.get(1234567890L));

    }

    @Test
    void deleteShouldReturnTrueOnSuccess() {
        EditorOut editorOut = editorService.create(mockIn);
        boolean actual = editorService.delete(editorOut.getId());
        assertTrue(actual);
    }
}