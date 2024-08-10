package com.coco.bakingbuddy.product.dto.request;

import com.coco.bakingbuddy.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EditProductRequestDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String productImageUrl;

    public static Product toEntity(EditProductRequestDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .productImageUrl(dto.getProductImageUrl())
                .build();
    }
}
