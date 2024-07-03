package com.coco.bakingbuddy.tag.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class TagRecipe extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "TAG_RECIPE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void addTag(Tag tag) {
        this.tag = tag;
    }
}
