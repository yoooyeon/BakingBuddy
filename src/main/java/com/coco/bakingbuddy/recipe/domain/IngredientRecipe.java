package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class IngredientRecipe extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_RECIPE_ID")
    private Long id;
    @Column(nullable = false)
    private BigDecimal amount;

    private Unit unit;
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
