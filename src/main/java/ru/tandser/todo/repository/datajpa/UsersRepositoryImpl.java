package ru.tandser.todo.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.repository.UsersRepository;

import java.util.List;

@Repository
public class UsersRepositoryImpl implements UsersRepository {

    private UsersJpaRepository usersJpaRepository;

    @Autowired
    public void setUsersJpaRepository(UsersJpaRepository usersJpaRepository) {
        this.usersJpaRepository = usersJpaRepository;
    }

    @Override
    public User get(int id) {
        return usersJpaRepository.findOne(id);
    }

    @Override
    public List<User> getAll() {
        return usersJpaRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return usersJpaRepository.findOneByEmail(email);
    }

    @Override
    public User getWithNotes(int id) {
        return usersJpaRepository.findOneWithNotes(id);
    }

    @Override
    public User remove(int id) {
        List<User> removed = usersJpaRepository.removeById(id);
        return !removed.isEmpty() ? removed.get(0) : null;
    }

    @Transactional
    @Override
    public User put(User user) {
        if (!user.isNew() && get(user.getId()) == null) {
            return null;
        }

        return usersJpaRepository.save(user);
    }

    @Override
    public int disabled(int id, boolean state) {
        return usersJpaRepository.setDisabled(id, state);
    }
}