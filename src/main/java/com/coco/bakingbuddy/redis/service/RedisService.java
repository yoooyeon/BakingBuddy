package com.coco.bakingbuddy.redis.service;

import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @Transactional
    public void saveSearchTerm(String term) {
        List<String> names = recipeQueryDslRepository.findByName(term);
        if (names != null && !names.isEmpty() && names.size() > 0) {
            String redisKey = "search_autocomplete:" + term;
            String[] values1 = names.toArray(new String[0]);
            redisTemplate.opsForSet().add(redisKey, values1);
        }
    }

    // 자동완성을 위한 검색어 제안 메서드
    public Set<String> autocomplete(String prefix) {
        String redisPattern = "search_autocomplete:" + prefix + "*";
        Set<String> keys = redisTemplate.keys(redisPattern);
        Set<String> autocompleteResults = new HashSet<>();
        if (keys != null) {
            for (String key : keys) {
                Set<Object> values = redisTemplate.opsForSet().members(key);
                if (values != null) {
                    autocompleteResults.addAll(values.stream()
                            .map(Object::toString)
                            .collect(Collectors.toSet()));
                }
            }
        }
        return autocompleteResults;
    }
}
