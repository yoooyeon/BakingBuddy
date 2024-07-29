package com.coco.bakingbuddy.redis.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.redis.repository.RedisAutoCompletePreviewDto;
import com.coco.bakingbuddy.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/redis")
@RequiredArgsConstructor
@RestController
public class RedisController {
    private final RedisService redisService;

    @PostMapping("term")
    public ResponseEntity<SuccessResponse<String>>
    saveSearchTerm(@RequestParam("term") String term) {
        redisService.saveSearchTerm(term);
        return toResponseEntity("검색어 저장 성공");
    }

    @GetMapping("autocomplete")
    public ResponseEntity<SuccessResponse<List<RedisAutoCompletePreviewDto>>>
    autocomplete(@RequestParam("prefix") String prefix) {
        return toResponseEntity("검색어 자동완성 조회 성공"
                , redisService.autocomplete(prefix));
    }
}
