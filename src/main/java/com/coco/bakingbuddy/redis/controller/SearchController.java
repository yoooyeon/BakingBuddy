package com.coco.bakingbuddy.redis.controller;

import com.coco.bakingbuddy.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/search")
public class SearchController {

    private final RedisService redisService;

    @GetMapping
    public String search() {
        return "recipe/recipe-search";
    }

    @ResponseBody
    @PostMapping("/term")
    public void saveSearchTerm(@RequestParam("term") String term) {
        redisService.saveSearchTerm(term);
    }

    @ResponseBody
    @GetMapping("/autocomplete")
    public Set<String> autocomplete(@RequestParam("prefix") String prefix) {
        return redisService.autocomplete(prefix);
    }
}
