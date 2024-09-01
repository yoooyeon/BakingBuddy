package com.coco.bakingbuddy.shopping.controller;

import com.coco.bakingbuddy.shopping.domain.ProductResponse;
import com.coco.bakingbuddy.shopping.service.ShoppingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping("/search")
    public ProductResponse searchProducts(
            @RequestParam("query") String query,
            @RequestParam(value = "display", required = false) Integer display,
            @RequestParam(value = "start", required = false) Integer start,
            @RequestParam(value = "sort", required = false) String sort) {
        return shoppingService.searchProducts(query, display, start, sort);
    }
}
