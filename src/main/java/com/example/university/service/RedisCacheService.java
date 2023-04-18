package com.example.university.service;

import com.example.university.dto.response.UserResponseDto;
import com.example.university.entity.UserEntity;
import com.example.university.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RedisCacheService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public String addUser(UserEntity user) throws JsonProcessingException {
        String userStr = new ObjectMapper().writeValueAsString(user);
        if (user.getRedisKey() != null) {
            redisTemplate.opsForValue().setIfPresent(user.getRedisKey(), userStr);
            return user.getRedisKey();
        }
        String redisKey = UUID.randomUUID().toString();
        user.setRedisKey(redisKey);
        UserEntity userEntity = userRepository.save(user);

        String userFromMongo = new ObjectMapper().writeValueAsString(user);
        System.out.println("USER WRITE AS STRING :" + userFromMongo);
        redisTemplate.opsForValue().set(redisKey, userFromMongo);
        return userEntity.getRedisKey();
    }
    public UserEntity getUserFromRedis(final String redisKey) throws JsonProcessingException {
        String s = redisTemplate.opsForValue().get(redisKey);
        return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(s, UserEntity.class);
    }
}
