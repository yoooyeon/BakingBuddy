package com.coco.bakingbuddy.ranking.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TermCounter {
    @Id
    @Column(name = "RANKING_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String term;
    private int count; // 검색 카운트
}
