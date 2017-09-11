package ru.tandser.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.repository.NoteRepository;
import ru.tandser.todo.service.exc.NotFoundException;

import java.util.List;

import static ru.tandser.todo.utils.Inspector.requireExist;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note get(int id, int userId) {
        return requireExist(noteRepository.get(id, userId));
    }

    @Override
    public List<Note> getAll(int userId) {
        return requireExist(noteRepository.getAll(userId));
    }

    @Override
    public void remove(int id, int userId) {
        requireExist(noteRepository.remove(id, userId));
    }

    @Override
    public Note save(Note note, int userId) {
        return requireExist(noteRepository.put(note, userId));
    }

    @Override
    public void update(Note note, int userId) {
        requireExist(noteRepository.put(note, userId));
    }

    @Override
    public void toggle(int id, boolean state, int userId) {
        if (noteRepository.toggle(id, state, userId) == 0) {
            throw new NotFoundException();
        }
    }
}