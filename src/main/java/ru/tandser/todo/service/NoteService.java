package ru.tandser.todo.service;

import ru.tandser.todo.domain.Note;

import java.util.List;

public interface NoteService {

    Note get(int id, int userId);

    List<Note> getAll(int userId);

    void remove(int id, int userId);

    Note save(Note note, int userId);

    void update(Note note, int userId);

    void toggle(int id, boolean state, int userId);
}