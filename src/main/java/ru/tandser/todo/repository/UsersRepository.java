package ru.tandser.todo.repository;

import ru.tandser.todo.domain.User;

import java.util.List;

public interface UsersRepository {

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);

    User getWithNotes(int id);

    User remove(int id);

    User put(User user);

    int disabled(int id, boolean state);
}