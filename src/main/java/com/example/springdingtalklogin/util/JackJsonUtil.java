package com.example.springdingtalklogin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mouxiaoshi
 */
public class JackJsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static  <T> T getObject(String str, Class<T> clazz) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(str, clazz);
    }

    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
