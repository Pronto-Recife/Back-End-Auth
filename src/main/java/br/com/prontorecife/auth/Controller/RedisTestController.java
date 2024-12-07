package br.com.prontorecife.auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/test-redis")
    public String testRedis(){
        redisTemplate.opsForValue().set("testKey", "Hello, Redis!");
        return redisTemplate.opsForValue().get("testKey");
    }
}