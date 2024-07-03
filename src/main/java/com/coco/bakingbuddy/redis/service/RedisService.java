package com.coco.bakingbuddy.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class RedisService {


    private final RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    // 검색어를 저장하는 메서드
    public void saveSearchTerm(String term) {
        redisTemplate.opsForSet().add("search_terms", term);
    }

    // 자동완성을 위한 검색어 제안 메서드
    public Set<String> autocomplete(String prefix) {
        // Redis의 Sorted Set을 이용하여 prefix로 시작하는 검색어를 찾아서 반환
        // 예시로는 Set을 사용하도록 수정
        return redisTemplate.keys(prefix + "*");
    }
}