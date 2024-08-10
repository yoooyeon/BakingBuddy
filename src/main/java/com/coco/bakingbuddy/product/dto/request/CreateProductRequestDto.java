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
public class CreateProductRequestDto {
    private String name;
    private Integer price;
    private String description;
    private String productImageUrl;

    public static Product toEntity(CreateProductRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .productImageUrl(dto.getProductImageUrl())
                .build();
    }
}
