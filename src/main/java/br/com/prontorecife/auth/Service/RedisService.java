package br.com.prontorecife.auth.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void addTokenToBlacklist(String token, long duration){
        redisTemplate.opsForValue().set(token, "blacklisted", duration, TimeUnit.SECONDS);
    }
    public boolean isTokenBlacklisted(String token){
        return redisTemplate.hasKey(token);
    }
}
