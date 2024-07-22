package com.coco.bakingbuddy.recipe.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
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
//    @JsonProperty("stepNumber")
    private Integer stepNumber; // 단계 번호
//    @JsonProperty("description")
    private String description; // 단계 설명
//    @JsonProperty("stepImage")
    private String stepImage; // 단계 이미지

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;


    public void updateImage(String imageUrl) {
        this.stepImage = imageUrl;
    }
}
