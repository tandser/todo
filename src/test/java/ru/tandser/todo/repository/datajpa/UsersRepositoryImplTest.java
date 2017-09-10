package ru.tandser.todo.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.UsersRepository;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.junit.Assert.*;
import static ru.tandser.todo.TestUserData.*;

public class UsersRepositoryImplTest extends AbstractRepositoryTest {

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Test
    public void testGet() {
        assertNull(usersRepository.get(nonExistentUser.getId()));
        assertTrue(USER_MATCHER.equals(admin, usersRepository.get(admin.getId())));
        assertTrue(USER_MATCHER.equals(user,  usersRepository.get(user.getId())));
    }

    @Test
    public void testGetAll() {
        assertTrue(USER_MATCHER.equals(Arrays.asList(admin, user), usersRepository.getAll()));
    }

    @Test
    public void testGetByEmail() {
        assertNull(usersRepository.getByEmail(nonExistentUser.getEmail()));
        assertTrue(USER_MATCHER.equals(admin, usersRepository.getByEmail(admin.getEmail())));
        assertTrue(USER_MATCHER.equals(user,  usersRepository.getByEmail(user.getEmail())));
    }

    @Test
    public void testGetWithNotes() {
        // TODO: реализовать
    }

    @Test
    public void testRemove() {
        assertNull(usersRepository.remove(nonExistentUser.getId()));
        assertTrue(USER_MATCHER.equals(admin, usersRepository.remove(admin.getId())));
        assertTrue(USER_MATCHER.equals(user,  usersRepository.remove(user.getId())));
    }

    @Test
    public void testPut() {
        assertNull(usersRepository.put(nonExistentUser));

        assertTrue(USER_MATCHER.equals(newUser, usersRepository.put(newUser)));
        assertTrue(USER_MATCHER.equals(newUser, usersRepository.get(newUser.getId())));

        newUser.setId(null);

        assertTrue(USER_MATCHER.equals(updatedUser, usersRepository.put(updatedUser)));
        assertTrue(USER_MATCHER.equals(updatedUser, usersRepository.get(updatedUser.getId())));
    }

    @Test
    public void testPutConflictingUser() {
        thrown.expect(ObjectOptimisticLockingFailureException.class);
        usersRepository.put(conflictingUser);
    }

    @Test
    public void testPutDuplicateUser() {
        thrown.expect(DataIntegrityViolationException.class);
        usersRepository.put(duplicateUser);
    }

    @Test
    public void testDisabled() {
        assertEquals(0, usersRepository.disabled(nonExistentUser.getId(), true));

        assertEquals(1, usersRepository.disabled(admin.getId(), true));
        assertTrue(usersRepository.get(admin.getId()).getDisabled());

        assertEquals(1, usersRepository.disabled(admin.getId(), false));
        assertFalse(usersRepository.get(admin.getId()).getDisabled());
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> usersRepository.put(invalidNameUser),     ConstraintViolationException.class);
        validateRootCause(() -> usersRepository.put(invalidEmailUser),    ConstraintViolationException.class);
        validateRootCause(() -> usersRepository.put(invalidPasswordUser), ConstraintViolationException.class);
    }
}