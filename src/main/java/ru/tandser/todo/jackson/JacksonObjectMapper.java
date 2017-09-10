package ru.tandser.todo.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonObjectMapper extends ObjectMapper {

    private static final ObjectMapper instance = new JacksonObjectMapper();

    private JacksonObjectMapper() {
        registerModule(new Hibernate5Module());
        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getInstance() {
        return instance;
    }
}