package com.fileservice.metadata.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheService {

   private static final Duration TTL =
           Duration.ofMinutes(10);

   private final RedisTemplate<String, Object> redisTemplate;

   public <T> T get(String key, Class<T> clazz) {
      Object o =  redisTemplate.opsForValue().get(key);
      if (o == null) {
         return null;
      }
      return clazz.isInstance(o) ? clazz.cast(o) : null;
   }

   public void set(String key, Object value){
      redisTemplate.opsForValue().set(key,value, TTL);
   }

   public void delete(String key) {
      redisTemplate.delete(key);
   }
}

