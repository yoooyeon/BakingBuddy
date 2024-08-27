package com.coco.bakingbuddy.shopping.service;

import com.coco.bakingbuddy.shopping.domain.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ShoppingService {
    private final String NAVER_API_DOMAIN = "https://openapi.naver.com/v1/search/shop.json";

    //    @Value("${naver.api.client-id}")
    private String clientId="UoMIUV2XU8pZenSQwq6N";

    //    @Value("${naver.api.client-secret}")
    private String clientSecret="xI_pZqAkvh" ;


    private final WebClient webClient;

    public ShoppingService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(NAVER_API_DOMAIN)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    public Mono<ProductResponse> searchProducts(String query, Integer display, Integer start, String sort) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("display", display != null ? display : 10)
                        .queryParam("start", start != null ? start : 1)
                        .queryParam("sort", sort != null ? sort : "sim")
                        .build())
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }
}
