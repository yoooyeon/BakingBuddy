package com.coco.bakingbuddy.ingredient.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
