package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private boolean openYn; // 공개 여부 True - Open

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients; // JPA
//    private String tags; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "recipe")
    private List<TagRecipe> tagRecipe;

    public void delete() {
        this.useYn = false;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients != null) {
            this.ingredients.add(ingredient);
            return;
        }
        this.ingredients = new ArrayList<>();
        this.ingredients.add(ingredient);
    }
}
