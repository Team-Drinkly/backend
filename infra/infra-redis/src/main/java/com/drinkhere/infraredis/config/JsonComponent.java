package com.drinkhere.infraredis.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JsonComponent {

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public String objectToJson(Object obj) {
        String jsonStr = "";
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            new RuntimeException("ParsingException", e);
        }
        return jsonStr;
    }

    public <T> T jsonToObject(String json, Class<T> clazz) {
        T obj = null;
        try {
            // 단순 문자열을 처리할 수 있도록 readValue를 명시적으로 호출
            if (clazz == String.class) {
                return clazz.cast(json); // 단순 문자열 그대로 반환
            }
            obj = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("ConvertException", e); // 예외를 던져 문제를 추적 가능하게 만듦
        }
        return obj;
    }

}