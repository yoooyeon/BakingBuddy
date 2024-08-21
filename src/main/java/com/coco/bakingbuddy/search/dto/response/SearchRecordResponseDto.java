package com.coco.bakingbuddy.search.dto.response;

import com.coco.bakingbuddy.search.domain.SearchRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SearchRecordResponseDto {
    private String search;
    private LocalDateTime timestamp;

    public static SearchRecordResponseDto fromEntity(SearchRecord dto) {
        return SearchRecordResponseDto.builder()
                .search(dto.getTerm())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
