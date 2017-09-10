package ru.tandser.todo.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.tandser.todo.TestNoteData.NOTE_MATCHER;
import static ru.tandser.todo.TestNoteData.userNotes;
import static ru.tandser.todo.TestUserData.*;

public class UserRepositoryImplTest extends AbstractRepositoryTest {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testGet() {
        assertNull(userRepository.get(nonExistentUser.getId()));
        assertTrue(USER_MATCHER.equals(user, userRepository.get(user.getId())));
    }

    @Test
    public void testGetAll() {
        assertTrue(USER_MATCHER.equals(Arrays.asList(admin, user), userRepository.getAll()));
    }

    @Test
    public void testGetByEmail() {
        assertNull(userRepository.getByEmail(nonExistentUser.getEmail()));
        assertTrue(USER_MATCHER.equals(user, userRepository.getByEmail(user.getEmail())));
    }

    @Test
    public void testGetWithNotes() {
        assertNull(userRepository.getWithNotes(nonExistentUser.getId()));
        assertTrue(NOTE_MATCHER.equals(userNotes, userRepository.getWithNotes(user.getId()).getNotes()));
    }

    @Test
    public void testRemove() {
        assertNull(userRepository.remove(nonExistentUser.getId()));

        assertTrue(USER_MATCHER.equals(user, userRepository.remove(user.getId())));
        assertNull(userRepository.get(user.getId()));
    }

    @Test
    public void testPut() {
        assertNull(userRepository.put(nonExistentUser));

        assertTrue(USER_MATCHER.equals(newUser, userRepository.put(newUser)));
        assertTrue(USER_MATCHER.equals(newUser, userRepository.get(newUser.getId())));

        newUser.setId(null);

        assertTrue(USER_MATCHER.equals(updatedUser, userRepository.put(updatedUser)));
        assertTrue(USER_MATCHER.equals(updatedUser, userRepository.get(updatedUser.getId())));
    }

    @Test
    public void testPutConflictingUser() {
        thrown.expect(ObjectOptimisticLockingFailureException.class);
        userRepository.put(conflictingUser);
    }

    @Test
    public void testPutDuplicateUser() {
        thrown.expect(DataIntegrityViolationException.class);
        userRepository.put(duplicateUser);
    }

    @Test
    public void testDisabled() {
        assertEquals(0, userRepository.toggle(nonExistentUser.getId(), true));

        assertEquals(1, userRepository.toggle(user.getId(), true));
        assertTrue(userRepository.get(user.getId()).getDisabled());
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> userRepository.put(invalidNameUser),     ConstraintViolationException.class);
        validateRootCause(() -> userRepository.put(invalidEmailUser),    ConstraintViolationException.class);
        validateRootCause(() -> userRepository.put(invalidPasswordUser), ConstraintViolationException.class);
    }
}