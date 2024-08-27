package com.coco.bakingbuddy.product.domain;


import com.coco.bakingbuddy.file.domain.ProductImageFile;
import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.review.domain.ProductReview;
import com.coco.bakingbuddy.search.domain.ClickableEntity;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Product extends BaseTime implements ClickableEntity {
    @Column(name = "PRODUCT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String link;
    private String productImageUrl;
    private List<String> productDetailImageUrls;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews;
    private boolean useYn;
    @Column(unique = true)
    private Long providerId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 상품 등록자

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequence ASC")
    @Builder.Default
    private List<ProductImageFile> productImageFiles = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setImageUrl(String imageUrl) {
        this.productImageUrl = imageUrl;
    }

    public void setDetailImageUrl(List<String> imageUrl) {
        this.productDetailImageUrls = imageUrl;
    }
}
