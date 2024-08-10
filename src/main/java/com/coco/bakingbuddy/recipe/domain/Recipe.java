package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.like.domain.Like;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
    @JsonIgnore

    @ManyToOne
    @JoinColumn(name = "DIRECTORY_ID")
    private Directory directory;
    private boolean useYn; // 삭제여부 N:삭제 상태
    @Column(length = 600)  // 최대 길이 설정 // @Lob
    private String description;
    private String openYn; // 공개 여부 True - Open
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도

    private Integer likeCount = 0;
    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeStep> recipeSteps; // 조리 단계
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<TagRecipe> tagRecipes;
    @JsonIgnore
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<IngredientRecipe> ingredientRecipes;
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeReview> reviews;

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

    public Recipe increaseLikeCount(Like like) {
        if (this.likeCount == null) {
            this.likeCount = 0; // 방어적으로 0으로 초기화
        }
        this.likeCount++;
        this.likes.add(like);
        return this;
    }

    public Recipe decreaseLikeCount(Like like) {
        if (this.likeCount == null) {
            this.likeCount = 0; // 방어적으로 0으로 초기화
        }
        if (this.likeCount > 0) {
            this.likeCount--;
        }
        this.likes.remove(like);
        return this;
    }
}
