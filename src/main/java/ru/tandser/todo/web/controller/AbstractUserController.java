package ru.tandser.todo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.service.UserService;
import ru.tandser.todo.web.Principal;

import java.util.List;

import static ru.tandser.todo.utils.Inspector.*;

public abstract class AbstractUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User get(int id) {
        Principal principal = Principal.get();
        logger.info("{}: get({})", principal.getUsername(), id);
        return userService.get(id);
    }

    public List<User> getAll() {
        Principal principal = Principal.get();
        logger.info("{}: getAll()", principal.getUsername());
        return userService.getAll();
    }

    public User getByEmail(String email) {
        Principal principal = Principal.get();
        logger.info("{}: getByEmail({})", principal.getUsername(), email);
        requireNotNull(email);
        return userService.getByEmail(email);
    }

    public User getWithNotes(int id) {
        Principal principal = Principal.get();
        logger.info("{}: getWithNotes({})", principal.getUsername(), id);
        return userService.getWithNotes(id);
    }

    public void remove(int id) {
        Principal principal = Principal.get();
        logger.info("{}: remove({})", principal.getUsername(), id);
        userService.remove(id);
    }

    public User save(User user) {
        Principal principal = Principal.get();
        logger.info("{}: save({})", principal.getUsername(), user);
        requireNew(user);
        return userService.save(user);
    }

    public void update(User user, int id) {
        Principal principal = Principal.get();
        logger.info("{}: update({}, {})", principal.getUsername(), user, id);
        requireConsistency(user, id);
        userService.update(user);
    }

    public void toggle(int id, boolean state) {
        Principal principal = Principal.get();
        logger.info("{}: toggle({}, {})", principal.getUsername(), id, state);
        userService.toggle(id, state);
    }
}