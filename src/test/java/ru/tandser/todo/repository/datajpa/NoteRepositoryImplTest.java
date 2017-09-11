package ru.tandser.todo.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.NoteRepository;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;
import static ru.tandser.todo.TestNoteData.*;
import static ru.tandser.todo.UserTestData.nonExistentUser;
import static ru.tandser.todo.UserTestData.user;

public class NoteRepositoryImplTest extends AbstractRepositoryTest {

    private NoteRepository noteRepository;

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Test
    public void testGet() {
        assertNull(noteRepository.get(existingNote.getId(),    nonExistentUser.getId()));
        assertNull(noteRepository.get(nonExistentNote.getId(), user.getId()));

        assertTrue(NOTE_MATCHER.equals(existingNote, noteRepository.get(existingNote.getId(), user.getId())));
    }

    @Test
    public void testGetAll() {
        assertNull(noteRepository.getAll(nonExistentUser.getId()));
        assertTrue(NOTE_MATCHER.equals(userNotes, noteRepository.getAll(user.getId())));
    }

    @Test
    public void testRemove() {
        assertNull(noteRepository.remove(existingNote.getId(),    nonExistentUser.getId()));
        assertNull(noteRepository.remove(nonExistentNote.getId(), user.getId()));

        assertTrue(NOTE_MATCHER.equals(existingNote, noteRepository.remove(existingNote.getId(), user.getId())));
        assertNull(noteRepository.get(existingNote.getId(), user.getId()));
    }

    @Test
    public void testPut() {
        assertNull(noteRepository.put(newNote, nonExistentUser.getId()));
        assertNull(newNote.getId());

        assertTrue(NOTE_MATCHER.equals(newNote, noteRepository.put(newNote, user.getId())));
        assertTrue(NOTE_MATCHER.equals(newNote, noteRepository.get(newNote.getId(), user.getId())));

        newNote.setId(null);

        assertTrue(NOTE_MATCHER.equals(updatedNote, noteRepository.put(updatedNote, user.getId())));
        assertTrue(NOTE_MATCHER.equals(updatedNote, noteRepository.get(updatedNote.getId(), user.getId())));
    }

    @Test
    public void testPutConflictingNote() {
        thrown.expect(ObjectOptimisticLockingFailureException.class);
        noteRepository.put(conflictingNote, user.getId());
    }

    @Test
    public void testDone() {
        assertEquals(0, noteRepository.toggle(existingNote.getId(),    true, nonExistentUser.getId()));
        assertEquals(0, noteRepository.toggle(nonExistentNote.getId(), true, user.getId()));

        assertEquals(1, noteRepository.toggle(existingNote.getId(), true, user.getId()));
        assertTrue(noteRepository.get(existingNote.getId(), user.getId()).getDone());
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> noteRepository.put(invalidTextNote, user.getId()), ConstraintViolationException.class);
    }
}