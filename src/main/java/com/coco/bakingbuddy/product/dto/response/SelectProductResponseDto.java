package com.coco.bakingbuddy.product.dto.response;

import com.coco.bakingbuddy.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectProductResponseDto {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private List<String> productImageUrls;
    private String username;
    private String profileImageUrl;

    public static SelectProductResponseDto fromEntity(Product product) {
        return SelectProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .productImageUrls(product.getProductImageUrls())
                .build();
    }
}
