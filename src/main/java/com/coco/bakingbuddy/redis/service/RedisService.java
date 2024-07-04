package com.coco.bakingbuddy.redis.service;

import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RecipeQueryDslRepository recipeQueryDslRepository;

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
        List<String> names = recipeQueryDslRepository.findByName(term);
        if (names != null && !names.isEmpty() && names.size() > 0) {
            String redisKey = "search_term:" + term;
            log.info("redisKey=" + redisKey);
            redisTemplate.opsForSet().add(redisKey, names.toArray(new String[0]));
            // 로그 확인용
            Set<Object> values = redisTemplate.opsForSet().members(redisKey);
            log.info("Values for {}: {}", redisKey, values);
        }
    }

    // 자동완성을 위한 검색어 제안 메서드
    public Set<String> autocomplete(String prefix) {
        // Redis의 Sorted Set을 이용하여 prefix로 시작하는 검색어를 찾아서 반환
        // 예시로는 Set을 사용하도록 수정
        return redisTemplate.keys("search_term:" + prefix + "*")
                .stream()
                .map(x -> x.substring("search_term:".length()))
                .collect(Collectors.toSet());
    }
}
