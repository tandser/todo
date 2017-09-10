package ru.tandser.todo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonConverter {

    private JsonConverter() {}

    public static String toJson(Object obj) {
        try {
            return JacksonObjectMapper.getInstance().writer().writeValueAsString(obj);
        } catch (JsonProcessingException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return JacksonObjectMapper.getInstance().readerFor(type).readValue(json);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static <T> T fromJson(File json, Class<T> type) {
        try {
            return JacksonObjectMapper.getInstance().readerFor(type).readValue(json);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> type) {
        try {
            return JacksonObjectMapper.getInstance().readerFor(type).<T>readValues(json).readAll();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static <T> List<T> fromJsonToList(File json, Class<T> type) {
        try {
            return JacksonObjectMapper.getInstance().readerFor(type).<T>readValues(json).readAll();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }
}