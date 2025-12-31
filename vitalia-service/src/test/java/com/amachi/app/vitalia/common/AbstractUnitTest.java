package com.amachi.app.vitalia.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base class for all Unit Tests.
 * Provides common configuration like ObjectMapper and utility methods for
 * loading JSON test data.
 * Pure Java/JUnit/Mockito environment. No Spring Context.
 */
public abstract class AbstractUnitTest {

    protected final ObjectMapper objectMapper;

    protected AbstractUnitTest() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Loads a JSON file from src/test/resources and maps it to a target Class.
     * 
     * @param path  Relative path resources (e.g.,
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
