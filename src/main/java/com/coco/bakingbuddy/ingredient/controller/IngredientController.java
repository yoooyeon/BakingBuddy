package com.coco.bakingbuddy.ingredient.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.ingredient.domain.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RequestMapping("/api/ingredients")
@RestController
public class IngredientController {
    @GetMapping("units")
    public ResponseEntity<SuccessResponse<List<String>>> test() {
        return toResponseEntity("단위 리스트 조회 성공", Unit.getAllDisplayNames());
    }
}
