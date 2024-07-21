package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recipe extends BaseTime {
    @Id
    @Column(name = "RECIPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "DIRECTORY_ID")
    private Directory directory;

    private boolean useYn;
    @Column(length = 600)  // 최대 길이 설정 // @Lob
    private String description;
    private String openYn; // 공개 여부 True - Open
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeStep> recipeSteps; // 조리 단계

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private Set<TagRecipe> tagRecipes;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<IngredientRecipe> ingredientRecipes;

    public void delete() {
        this.useYn = false;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateImage(String imageUrl) {
        this.recipeImageUrl = imageUrl;
    }

    public void setRecipeSteps(List<RecipeStep> steps) {
        if (this.recipeSteps != null) {
            this.recipeSteps.clear();
            this.recipeSteps.addAll(steps);
        } else {
            this.recipeSteps = steps;
        }
        for (RecipeStep step : steps) {
            step.setRecipe(this);
        }
    }
}
