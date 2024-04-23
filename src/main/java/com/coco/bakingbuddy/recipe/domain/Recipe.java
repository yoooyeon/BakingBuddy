package com.coco.bakingbuddy.recipe.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private Long dirId;
    private boolean useYn;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private String ingredients; // JPA
    private String tags; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도


    public void delete() {
        this.useYn = false;
    }
}
