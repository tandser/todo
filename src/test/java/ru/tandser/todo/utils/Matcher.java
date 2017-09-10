package ru.tandser.todo.utils;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.tandser.todo.jackson.JsonConverter.*;

public class Matcher<T> {

    private Class<T>          type;
    private BiPredicate<T, T> comparator;

    public Matcher(Class<T> type, BiPredicate<T, T> comparator) {
        this.type       = requireNonNull(type);
        this.comparator = requireNonNull(comparator);
    }

    private class Wrapper {

        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Wrapper that = (Wrapper) obj;

            return entity != null && that.entity != null
                    ? comparator.test(entity, that.entity)
                    : entity == null && that.entity == null;
        }
    }

    private abstract class HamcrestMatcher<E> extends BaseMatcher<String> {

        private E expected;

        private HamcrestMatcher(E expected) {
            this.expected = expected;
        }

        protected abstract boolean compare(E expected, String body);

        @Override
        public boolean matches(Object item) {
            return compare(expected, (String) item);
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(toJson(expected));
        }
    }

    private List<Wrapper> wrap(List<T> list) {
        return list.stream().map(Wrapper::new).collect(Collectors.toList());
    }

    public boolean equals(T expected, T actual) {
        return Objects.equals(new Wrapper(expected), new Wrapper(actual));
    }

    public boolean equals(List<T> expected, List<T> actual) {
        return Objects.equals(wrap(expected), wrap(actual));
    }

    public ResultMatcher contentMatcher(T expected) {
        return content().string(new HamcrestMatcher<T>(expected) {
            @Override
            protected boolean compare(T expected, String body) {
                return Matcher.this.equals(expected, fromJson(body, type));
            }
        });
    }

    public ResultMatcher contentMatcher(List<T> expected) {
        return content().string(new HamcrestMatcher<List<T>>(expected) {
            @Override
            protected boolean compare(List<T> expected, String body) {
                return Matcher.this.equals(expected, fromJsonToList(body, type));
            }
        });
    }
}