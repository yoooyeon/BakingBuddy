package com.coco.bakingbuddy.tag.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag extends BaseTime {
    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 태그명
    @JsonIgnore

    @OneToMany(mappedBy = "tag")
    private List<TagRecipe> tagRecipe;
}
