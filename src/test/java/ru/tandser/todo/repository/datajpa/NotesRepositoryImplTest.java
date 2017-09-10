package ru.tandser.todo.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.NotesRepository;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;
import static ru.tandser.todo.TestNoteData.*;
import static ru.tandser.todo.TestUserData.nonExistentUser;
import static ru.tandser.todo.TestUserData.user;

public class NotesRepositoryImplTest extends AbstractRepositoryTest {

    private NotesRepository notesRepository;

    @Autowired
    public void setNotesRepository(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Test
    public void testGet() {
        assertNull(notesRepository.get(existingNote.getId(),    nonExistentUser.getId()));
        assertNull(notesRepository.get(nonExistentNote.getId(), user.getId()));

        assertTrue(NOTE_MATCHER.equals(existingNote, notesRepository.get(existingNote.getId(), user.getId())));
    }

    @Test
    public void testGetAll() {
        assertNull(notesRepository.getAll(nonExistentUser.getId()));
        assertTrue(NOTE_MATCHER.equals(userNotes, notesRepository.getAll(user.getId())));
    }

    @Test
    public void testRemove() {
        assertNull(notesRepository.remove(existingNote.getId(),    nonExistentUser.getId()));
        assertNull(notesRepository.remove(nonExistentNote.getId(), user.getId()));

        assertTrue(NOTE_MATCHER.equals(existingNote, notesRepository.remove(existingNote.getId(), user.getId())));
        assertNull(notesRepository.get(existingNote.getId(), user.getId()));
    }

    @Test
    public void testPut() {
        assertNull(notesRepository.put(newNote, nonExistentUser.getId()));
        assertNull(newNote.getId());

        assertTrue(NOTE_MATCHER.equals(newNote, notesRepository.put(newNote, user.getId())));
        assertTrue(NOTE_MATCHER.equals(newNote, notesRepository.get(newNote.getId(), user.getId())));

        newNote.setId(null);

        assertTrue(NOTE_MATCHER.equals(updatedNote, notesRepository.put(updatedNote, user.getId())));
        assertTrue(NOTE_MATCHER.equals(updatedNote, notesRepository.get(updatedNote.getId(), user.getId())));
    }

    @Test
    public void testPutConflictingNote() {
        thrown.expect(ObjectOptimisticLockingFailureException.class);
        notesRepository.put(conflictingNote, user.getId());
    }

    @Test
    public void testDone() {
        assertEquals(0, notesRepository.done(existingNote.getId(),    true, nonExistentUser.getId()));
        assertEquals(0, notesRepository.done(nonExistentNote.getId(), true, user.getId()));

        assertEquals(1, notesRepository.done(existingNote.getId(), true, user.getId()));
        assertTrue(notesRepository.get(existingNote.getId(), user.getId()).getDone());
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> notesRepository.put(invalidTextNote, user.getId()), ConstraintViolationException.class);
    }
}