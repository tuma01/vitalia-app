package com.amachi.app.core.common.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base support class for all Unit Tests.
 * Provides common configuration like ObjectMapper and utility methods for
 * loading JSON test data.
 * This class is located in src/main/java to be easily shared across modules
 * without complex Maven test-jar configurations.
 */
public abstract class AbstractTestSupport {

    protected final ObjectMapper objectMapper;

    protected AbstractTestSupport() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Loads a JSON file from src/test/resources and maps it to a target Class.
     * 
     * @param path  Relative path to resources (e.g.,
     *              "data/tenantadmin/tenantadmin-entity.json")
     * @param clazz Target class
     * @param <T>   Type
     * @return Deserialized object
     */
    protected <T> T loadJson(String path, Class<T> clazz) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found in classpath: " + path);
            }
            return objectMapper.readValue(is, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON from path: " + path, e);
        }
    }
}
