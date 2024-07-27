package com.coco.bakingbuddy.recipe.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class PageResponseDto<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public PageResponseDto(List<T> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

}