package com.coco.bakingbuddy.recipe.domain;


import com.coco.bakingbuddy.file.domain.RecipeStepImageFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private String stepImage; // 단계 이미지
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @OneToMany(mappedBy = "recipeStep", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequence ASC")
    private List<RecipeStepImageFile> imageFiles;


    public void updateImage(String imageUrl) {
        this.stepImage = imageUrl;
    }
}
