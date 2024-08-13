package com.coco.bakingbuddy.product.dto.response;

import com.coco.bakingbuddy.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateProductResponseDto {
    private String name;
    private Integer price;
    private String description;
    private MultipartFile productImage;

    public static CreateProductResponseDto fromEntity(Product product) {
        return CreateProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
