package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.tag.domain.Tag;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class IngredientRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "INGREDIENT_RECIPE_ID")
    private Long id;

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
