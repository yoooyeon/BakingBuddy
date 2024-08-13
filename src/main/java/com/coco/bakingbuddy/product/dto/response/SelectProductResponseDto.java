package com.coco.bakingbuddy.product.dto.response;

import com.coco.bakingbuddy.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectProductResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String productImageUrl;
    private String username;
    private String profileImageUrl;

    public static SelectProductResponseDto fromEntity(Product product) {
        return SelectProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .productImageUrl(product.getProductImageUrl())
                .build();
    }
}
