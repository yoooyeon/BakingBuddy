package com.coco.bakingbuddy.recipe.domain;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeStep {
    @Id
    @Column(name = "RECIPE_STEP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stepNumber; // 단계 번호
    private String description; // 단계 설명

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;
}
