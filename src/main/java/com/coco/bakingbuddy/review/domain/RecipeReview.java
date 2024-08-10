package com.coco.bakingbuddy.review.domain;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class RecipeReview extends Review {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    @Override
    public void setRelatedEntity(Object entity) {
        if (entity instanceof Recipe) {
            this.recipe = (Recipe) entity;
        } else {
            throw new IllegalArgumentException("Invalid entity type for RecipeReview");
        }
    }
}
