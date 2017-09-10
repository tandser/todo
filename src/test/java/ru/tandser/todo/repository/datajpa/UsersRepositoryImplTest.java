package ru.tandser.todo.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tandser.todo.repository.AbstractRepositoryTest;
import ru.tandser.todo.repository.UsersRepository;

public class UsersRepositoryImplTest extends AbstractRepositoryTest {

    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
}