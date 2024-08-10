package com.coco.bakingbuddy.product.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseTime {
    @Column(name = "PRODUCT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String productImageUrl;
    private boolean useYn;

}
