package ru.tandser.todo;

import ru.tandser.todo.domain.User;
import ru.tandser.todo.utils.Matcher;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Objects;

import static org.springframework.util.ResourceUtils.getFile;
import static ru.tandser.todo.jackson.JsonConverter.fromJsonToList;

public class TestUserData {

    public static User admin;
    public static User user;
    public static User newUser;
    public static User updatedUser;
    public static User nonExistentUser;
    public static User conflictingUser;
    public static User duplicateUser;
    public static User invalidNameUser;
    public static User invalidEmailUser;
    public static User invalidPasswordUser;

    public static final Matcher<User> USER_MATCHER = new Matcher<>(User.class, (expected, actual) ->
            expected == actual || (Objects.equals(expected.getName(),     actual.getName())  &&
                                   Objects.equals(expected.getEmail(),    actual.getEmail()) &&
                                   Objects.equals(expected.getRole(),     actual.getRole())  &&
                                   Objects.equals(expected.getDisabled(), actual.getDisabled())));

    private TestUserData() {}

    public static void loadMocks() throws FileNotFoundException {
        Iterator<User> mocks = fromJsonToList(getFile("classpath:mocks/users.json"), User.class).iterator();

        admin               = mocks.next();
        user                = mocks.next();
        newUser             = mocks.next();
        updatedUser         = mocks.next();
        nonExistentUser     = mocks.next();
        conflictingUser     = mocks.next();
        duplicateUser       = mocks.next();
        invalidNameUser     = mocks.next();
        invalidEmailUser    = mocks.next();
        invalidPasswordUser = mocks.next();
    }
}