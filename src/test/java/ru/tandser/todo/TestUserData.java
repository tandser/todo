package ru.tandser.todo;

import org.springframework.util.ResourceUtils;
import ru.tandser.todo.domain.User;
import ru.tandser.todo.jackson.JsonConverter;
import ru.tandser.todo.utils.Matcher;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Objects;

public class TestUserData {

    public static User admin;
    public static User user;

    public static final Matcher<User> USER_MATCHER = new Matcher<>(User.class, (expected, actual) ->
            expected == actual || (Objects.equals(expected.getName(),     actual.getName())  &&
                                   Objects.equals(expected.getEmail(),    actual.getEmail()) &&
                                   Objects.equals(expected.getRole(),     actual.getRole())  &&
                                   Objects.equals(expected.getDisabled(), actual.getDisabled())));

    private TestUserData() {}

    public static void loadMocks() throws FileNotFoundException {
        Iterator<User> mocks = JsonConverter.fromJsonToList(ResourceUtils.getFile("classpath:mocks/users.json"), User.class).iterator();

        admin = mocks.next();
        user  = mocks.next();
    }
}