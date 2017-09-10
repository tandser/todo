package ru.tandser.todo.repository.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.UsersRepository;

import static org.junit.Assert.assertTrue;
import static ru.tandser.todo.TestUserData.*;

public class UsersRepositoryImplTest extends AbstractRepositoryTest {

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Test
    public void testGet() {
        assertTrue(USER_MATCHER.equals(admin, usersRepository.get(admin.getId())));
        assertTrue(USER_MATCHER.equals(user,  usersRepository.get(user.getId())));
    }
}