package com.automation.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Thin wrapper around Jackson so tests deal with model objects, not raw JSON strings.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private JsonUtils() {
    }

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not serialise " + value, e);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not deserialise into " + type.getSimpleName(), e);
        }
    }

    public static <T> T fromFile(String path, Class<T> type) {
        try {
            return MAPPER.readValue(new File(path), type);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read JSON file: " + path, e);
        }
    }
}
