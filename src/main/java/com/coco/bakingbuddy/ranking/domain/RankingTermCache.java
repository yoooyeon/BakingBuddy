package com.coco.bakingbuddy.ranking.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RankingTermCache extends BaseTime {
    @Id
    @Column(name = "RANKING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String term;
    private int count; // 검색 카운트

}
