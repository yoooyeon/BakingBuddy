package com.coco.bakingbuddy.ranking.dto.response;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectRankingTermsCacheResponseDto {
    private Long id;
    private String term;

    public static SelectRankingTermsCacheResponseDto fromEntity(RankingTermCache ranking) {
        return SelectRankingTermsCacheResponseDto.builder()
                .id(ranking.getId())
                .term(ranking.getTerm())
                .build();
    }
}
