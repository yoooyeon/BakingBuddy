package com.coco.bakingbuddy.recipe.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ingredient {
    @Id
    @Column(name = "INGREDIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "ingredient")
    private List<IngredientRecipe> ingredientRecipes;

}
