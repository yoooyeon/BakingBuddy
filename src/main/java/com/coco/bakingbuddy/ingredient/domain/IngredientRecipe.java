package com.coco.bakingbuddy.ingredient.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class IngredientRecipe extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_RECIPE_ID")
    private Long id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Unit unit;
    @Column(nullable = false)
    private Integer servings; // 몇인분 기준
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

}
