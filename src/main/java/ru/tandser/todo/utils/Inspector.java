package ru.tandser.todo.utils;

import ru.tandser.todo.domain.AbstractEntity;
import ru.tandser.todo.service.exc.NotFoundException;
import ru.tandser.todo.web.exc.BadRequestException;

public class Inspector {

    private Inspector() {}

    public static <T> T requireExist(T result) {
        if (result == null) {
            throw new NotFoundException();
        }

        return result;
    }

    public static void requireNotNull(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new BadRequestException();
            }
        }
    }

    public static void requireNew(AbstractEntity entity) {
        if (entity == null || !entity.isNew()) {
            throw new BadRequestException();
        }
    }

    public static void requireConsistency(AbstractEntity entity, int id) {
        if (entity == null || (!entity.isNew() && entity.getId() != id)) {
            throw new BadRequestException();
        }

        entity.setId(id);
    }
}