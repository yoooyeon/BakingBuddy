package com.coco.bakingbuddy.product.domain;


import com.coco.bakingbuddy.file.domain.ProductImageFile;
import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.review.domain.ProductReview;
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
public class Product extends BaseTime {
    @Column(name = "PRODUCT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private List<String> productImageUrls;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews;
    private boolean useYn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 상품 등록자

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequence ASC")
    private List<ProductImageFile> productImageFiles = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void setImageUrls(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            productImageUrls.add(imageUrl);
        }
    }
}
