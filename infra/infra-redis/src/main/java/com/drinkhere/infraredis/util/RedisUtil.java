package com.drinkhere.infraredis.util;

import com.drinkhere.infraredis.config.JsonComponent;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtil {

    final RedisTemplate<String, String> redisTemplate;
    final JsonComponent jsonComponent;

    public <T> T getObjectByKey(String key, Class<T> clazz) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();

        String jsonString = vop.get(key);
        if (StringUtils.isNotEmpty(jsonString)) {
            return jsonComponent.jsonToObject(jsonString, clazz);
        } else {
            return null;
        }
    }

    public void setObjectByKey(String key, Object obj) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(key, jsonComponent.objectToJson(obj));
        redisTemplate.expire(key, 7, TimeUnit.DAYS);
    }
}