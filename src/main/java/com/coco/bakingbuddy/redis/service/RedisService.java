package com.coco.bakingbuddy.redis.service;

import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.redis.repository.RedisAutoCompletePreviewDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RecipeQueryDslRepository recipeQueryDslRepository;

    public void setValue(String key, RedisAutoCompletePreviewDto value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    @Transactional
    public void saveSearchTerm(String term) {
        List<RedisAutoCompletePreviewDto> dtos = recipeQueryDslRepository.findPreviewByTerm(term);
        if (dtos != null && !dtos.isEmpty()) {
            String redisKey = "search_autocomplete:" + term;
            for (RedisAutoCompletePreviewDto dto : dtos) {
                redisTemplate.opsForSet().add(redisKey,
                        RedisAutoCompletePreviewDto.builder()
                                .imageUrl(dto.getImageUrl())
                                .name(dto.getName())
                                .recipeId(dto.getRecipeId())
                                .build());
            }
        }
    }

    @Transactional(readOnly = true)
    public List<RedisAutoCompletePreviewDto> autocomplete(String prefix) {
        String redisPattern = "search_autocomplete:" + prefix + "*";
        Set<String> keys = redisTemplate.keys(redisPattern);
        List<RedisAutoCompletePreviewDto> autocompleteResults = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        if (keys != null && !keys.isEmpty() && keys.size() > 0) {
            for (String key : keys) {
                Set<Object> values = redisTemplate.opsForSet().members(key);
                if (values != null) {
                    for (Object value : values) {
                        log.info("value={}", value);
                        RedisAutoCompletePreviewDto dto = mapper.convertValue(value, RedisAutoCompletePreviewDto.class);
                        autocompleteResults.add(dto);
                        log.info(dto.getImageUrl());
                        log.info(dto.getName());
                        log.info(""+dto.getRecipeId());
                    }
                }
            }
        }
        return autocompleteResults;
    }

}