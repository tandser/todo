package ru.tandser.todo.repository;

import ru.tandser.todo.domain.Note;

import java.util.List;

public interface NoteRepository {

    Note get(int id, int userId);

    List<Note> getAll(int userId);

    Note remove(int id, int userId);

    Note put(Note note, int userId);

    int done(int id, boolean state, int userId);
}