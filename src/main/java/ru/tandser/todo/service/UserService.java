package ru.tandser.todo.service;

import ru.tandser.todo.domain.User;

import java.util.List;

public interface UserService {

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    User getWithNotes(int id);

    void remove(int id);

    User save(User user);

    void update(User user);


}