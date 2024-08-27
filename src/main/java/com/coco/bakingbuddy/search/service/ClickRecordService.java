package com.coco.bakingbuddy.search.service;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.product.repository.ProductRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.search.domain.ClickRecord;
import com.coco.bakingbuddy.search.domain.ClickType;
import com.coco.bakingbuddy.search.domain.ClickableEntity;
import com.coco.bakingbuddy.search.dto.request.CreateClickRequestDto;
import com.coco.bakingbuddy.search.repository.ClickRecordRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ClickRecordService {
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    private final ClickRecordRepository clickRecordRepository;

    public void recordClick(User user, CreateClickRequestDto dto) {
        ClickRecord clickRecord = ClickRecord.builder()
                .user(user)
                .clickType(ClickType.from(dto.getClickType()))
                .entityId(dto.getId())
                .build();
        clickRecordRepository.save(clickRecord);
    }

    public ClickableEntity getClickableEntity(Long id, ClickType clickType) {
        switch (clickType) {
            case RECIPE:
                return recipeRepository.findById(id)
                        .orElseThrow(() -> new CustomException(RECIPE_NOT_FOUND));
            case PRODUCT:
                return productRepository.findById(id)
                        .orElseThrow(() -> new CustomException(PRODUCT_NOT_FOUND));
            default:
                throw new CustomException(INVALID_CLICK_TYPE);
        }
    }
}
