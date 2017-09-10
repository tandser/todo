package ru.tandser.todo.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.repository.UserRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User get(int id) {
        return userJpaRepository.findOne(id);
    }

    @Override
    public List<User> getAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return userJpaRepository.findOneByEmail(email);
    }

    @Override
    public User getWithNotes(int id) {
        return userJpaRepository.findOneWithNotes(id);
    }

    @Override
    public User remove(int id) {
        List<User> removed = userJpaRepository.removeById(id);
        return !removed.isEmpty() ? removed.get(0) : null;
    }

    @Transactional
    @Override
    public User put(User user) {
        if (!user.isNew() && get(user.getId()) == null) {
            return null;
        }

        return userJpaRepository.save(user);
    }

    @Override
    public int toggle(int id, boolean state) {
        return userJpaRepository.setDisabled(id, state);
    }
}