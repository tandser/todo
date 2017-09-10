package ru.tandser.todo.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.Note;
import ru.tandser.todo.repository.NotesRepository;

import java.util.List;

@Repository
public class NotesRepositoryImpl implements NotesRepository {

    private NotesJpaRepository notesJpaRepository;
    private UsersJpaRepository usersJpaRepository;

    @Autowired
    public void setNotesJpaRepository(NotesJpaRepository notesJpaRepository) {
        this.notesJpaRepository = notesJpaRepository;
    }

    @Autowired
    public void setUsersJpaRepository(UsersJpaRepository usersJpaRepository) {
        this.usersJpaRepository = usersJpaRepository;
    }

    @Override
    public Note get(int id, int userId) {
        return notesJpaRepository.findOneByIdAndUserId(id, userId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Note> getAll(int userId) {
        return usersJpaRepository.exists(userId)
                ? notesJpaRepository.findAllByUserId(userId)
                : null;
    }

    @Override
    public Note remove(int id, int userId) {
        List<Note> removed = notesJpaRepository.removeByIdAndUserId(id, userId);
        return !removed.isEmpty() ? removed.get(0) : null;
    }

    @Transactional
    @Override
    public Note put(Note note, int userId) {
        if (!usersJpaRepository.exists(userId)) {
            return null;
        }

        if (!note.isNew() && get(note.getId(), userId) == null) {
            return null;
        }

        note.setUser(usersJpaRepository.getOne(userId));

        return notesJpaRepository.save(note);
    }

    @Override
    public int done(int id, boolean state, int userId) {
        return notesJpaRepository.setDone(id, state, userId);
    }
}