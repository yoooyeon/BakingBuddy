package com.coco.bakingbuddy.search.dto;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.search.domain.RecentSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RecentSearchResponseDto {
    private String search;
    private LocalDateTime timestamp;
    public static RecentSearchResponseDto fromEntity(RecentSearch dto) {
        return RecentSearchResponseDto.builder()
                .search(dto.getTerm())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
