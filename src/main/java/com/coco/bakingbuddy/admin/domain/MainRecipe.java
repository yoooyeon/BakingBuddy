package com.coco.bakingbuddy.admin.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MainRecipe extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAIN_RECIPE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPE_ID", nullable = false)
    private Recipe recipe; // 메인 페이지에 표시된 레시피

    @Column(nullable = false)
    private boolean isCurrent; // 현재 메인 레시피 여부

}
