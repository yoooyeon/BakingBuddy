package com.coco.bakingbuddy.shopping.service;
import com.coco.bakingbuddy.shopping.domain.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
@Service
public class ShoppingService {

    private final RestTemplate restTemplate;
    private final String NAVER_API_DOMAIN = "https://openapi.naver.com/v1/search/shop.json";

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    public ShoppingService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("X-Naver-Client-Id", clientId);
                    request.getHeaders().add("X-Naver-Client-Secret", clientSecret);
                    return execution.execute(request, body);
                })
                .build();
    }

    public ProductResponse searchProducts(String query, Integer display, Integer start, String sort) {
        String url = UriComponentsBuilder.fromHttpUrl(NAVER_API_DOMAIN)
                .queryParam("query", query)
                .queryParam("display", display != null ? display : 10)
                .queryParam("start", start != null ? start : 1)
                .queryParam("sort", sort != null ? sort : "sim")
                .toUriString();

        return restTemplate.getForObject(url, ProductResponse.class);
    }
}
