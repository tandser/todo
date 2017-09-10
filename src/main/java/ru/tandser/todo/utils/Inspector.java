package ru.tandser.todo.utils;

import ru.tandser.todo.service.exc.NotFoundException;

public class Inspector {

    private Inspector() {}

    public static <T> T requireExist(T result) {
        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }
}