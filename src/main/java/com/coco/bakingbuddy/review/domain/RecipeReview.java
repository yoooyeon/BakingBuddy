package com.coco.bakingbuddy.review.domain;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecipeReview  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating; // 1-5점 사이의 평점
}
