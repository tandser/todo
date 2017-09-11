package ru.tandser.todo.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tandser.todo.service.exc.NotFoundException;

import java.util.Arrays;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static ru.tandser.todo.TestNoteData.NOTE_MATCHER;
import static ru.tandser.todo.TestNoteData.userNotes;
import static ru.tandser.todo.UserTestData.*;

public class UserServiceImplTest extends AbstractServiceTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testGet() {
        assertTrue(USER_MATCHER.equals(user, userService.get(user.getId())));
    }

    @Test
    public void testGetNonExistentUser() {
        thrown.expect(NotFoundException.class);
        userService.get(nonExistentUser.getId());
    }

    @Test
    public void testGetAll() {
        assertTrue(USER_MATCHER.equals(Arrays.asList(admin, user), userService.getAll()));
    }

    @Test
    public void testGetByEmail() {
        assertTrue(USER_MATCHER.equals(user, userService.getByEmail(user.getEmail())));
    }

    @Test
    public void testByNonExistentEmail() {
        thrown.expect(NotFoundException.class);
        userService.getByEmail(nonExistentUser.getEmail());
    }

    @Test
    public void testGetWithNotes() {
        assertTrue(NOTE_MATCHER.equals(userNotes, userService.getWithNotes(user.getId()).getNotes()));
    }

    @Test
    public void testGetNonExistentUserWithNotes() {
        thrown.expect(NotFoundException.class);
        assertNull(userService.getWithNotes(nonExistentUser.getId()));
    }

    @Test
    public void testRemove() {

    }
}