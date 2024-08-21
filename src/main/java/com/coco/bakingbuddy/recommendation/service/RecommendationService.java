package com.coco.bakingbuddy.recommendation.service;

import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recommendation.repository.ProductRecommendationQueryDslRepository;
import com.coco.bakingbuddy.search.repository.SearchRecordRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    private final ProductRecommendationQueryDslRepository productRecommendationQueryDslRepository;
    private final SearchRecordRepository searchRecordRepository;
    public List<SelectProductResponseDto> selectByRecipeId(Long recipeId) {
      return  productRecommendationQueryDslRepository.findByRecipeId(recipeId)
              .stream().map(SelectProductResponseDto::fromEntity).collect(Collectors.toList());
    }

}
