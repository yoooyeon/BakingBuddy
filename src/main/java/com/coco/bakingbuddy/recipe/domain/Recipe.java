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
    private String memo;
    private String openYn; // 공개 여부 True - Open
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도
    private Integer viewCount; // 조회 수 todo userid 관리해야함
    private Integer hearts; // 좋아요 수 todo userid 관리해야함
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

    @PrePersist
    protected void onCreate() {
        if (this.viewCount == null) {
            this.viewCount = 0;
        }
        if (this.hearts == null) {
            this.hearts = 0;
        }
    }
}
