package ru.tandser.todo;

import ru.tandser.todo.domain.Note;
import ru.tandser.todo.utils.Matcher;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

import static org.springframework.util.ResourceUtils.getFile;
import static ru.tandser.todo.jackson.JsonConverter.fromJsonToList;

public class TestNoteData {

    public static List<Note> adminNotes;
    public static List<Note> userNotes;

    public static Note newNote;
    public static Note updatedNote;
    public static Note existingNote;
    public static Note nonExistentNote;
    public static Note conflictingNote;
    public static Note invalidTextNote;

    public static final Matcher<Note> NOTE_MATCHER = new Matcher<>(Note.class, (expected, actual) ->
            expected == actual || (Objects.equals(expected.getText(), actual.getText()) &&
                                   Objects.equals(expected.getDone(), actual.getDone())));

    private TestNoteData() {}

    public static void loadMocks() throws FileNotFoundException {
        List<Note> mocks = fromJsonToList(getFile("classpath:mocks/notes.json"), Note.class);

        adminNotes      = mocks.subList(0, 3);
        userNotes       = mocks.subList(3, 6);
        newNote         = mocks.get(6);
        updatedNote     = mocks.get(7);
        existingNote    = mocks.get(3);
        nonExistentNote = mocks.get(8);
        conflictingNote = mocks.get(9);
        invalidTextNote = mocks.get(10);
    }
}