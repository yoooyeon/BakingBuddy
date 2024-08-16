package com.coco.bakingbuddy.file.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductImageFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String ext;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String uploadPath;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private int sequence; // 이미지 순서

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
