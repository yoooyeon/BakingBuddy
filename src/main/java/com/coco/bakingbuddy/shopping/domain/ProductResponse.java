package com.coco.bakingbuddy.shopping.domain;

import lombok.Data;
import java.util.List;

@Data
public class ProductResponse {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Data
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String lprice;  // 가격을 문자열로 유지
        private String hprice;  // 가격을 문자열로 유지
        private String mallName;
        private String productId;  // 상품 ID를 문자열로 유지
        private String productType;  // 상품 유형을 문자열로 유지
        private String brand;
        private String maker;
        private String category1;
        private String category2;
        private String category3;
        private String category4;
    }
}
