package ru.tandser.todo.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.repository.NoteRepository;

import java.util.List;

@Repository
public class NoteRepositoryImpl implements NoteRepository {

    private NoteJpaRepository noteJpaRepository;
    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setNoteJpaRepository(NoteJpaRepository noteJpaRepository) {
        this.noteJpaRepository = noteJpaRepository;
    }

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Note get(int id, int userId) {
        return noteJpaRepository.findOneByIdAndUserId(id, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Note> getAll(int userId) {
        return userJpaRepository.exists(userId)
                ? noteJpaRepository.findAllByUserId(userId)
                : null;
    }

    @Override
    public Note remove(int id, int userId) {
        List<Note> removed = noteJpaRepository.removeByIdAndUserId(id, userId);
        return !removed.isEmpty() ? removed.get(0) : null;
    }

    @Transactional
    @Override
    public Note put(Note note, int userId) {
        if (!userJpaRepository.exists(userId)) {
            return null;
        }

        if (!note.isNew() && get(note.getId(), userId) == null) {
            return null;
        }

        note.setUser(userJpaRepository.getOne(userId));

        return noteJpaRepository.save(note);
    }

    @Override
    public int toggle(int id, boolean state, int userId) {
        return noteJpaRepository.setDone(id, state, userId);
    }
}