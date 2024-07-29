package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Ingredient extends BaseTime {
    @Id
    @Column(name = "INGREDIENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore

    @OneToMany(mappedBy = "ingredient")
    private List<IngredientRecipe> ingredientRecipes;

}
