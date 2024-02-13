package com.sellde.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.UUID;

public class TestUtil {

    public static UUID uuid() {
        return UUID.randomUUID();
    }

    public static String uuidAsString() {
        return UUID.randomUUID().toString();
    }

    public static <T> T readJsonFile(String filename, Class<T> tClass) {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(new File("src/test/resources/"+filename), tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}




