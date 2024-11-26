package com.drinkhere.apihealthcheck.presentations;

import com.drinkhere.infraredis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health-check")
@RequiredArgsConstructor
public class HealthCheckController {

    private final RedisUtil redisUtil;
    @GetMapping
    public String healthCheck() {
        return "This Turn is Blue!";
    }

    @GetMapping("redis")
    public String redisConnectionCheck() {
        // Redis에서 'id1' 키로 값을 가져옵니다.
        String value = (String) redisUtil.getValue("id1");

        // Redis에서 가져온 값이 있을 경우 반환, 없으면 기본 메시지 반환
        if (value != null) {
            return "value";
        } else {
            return "No value found for key 'id1'";
        }
    }
}
