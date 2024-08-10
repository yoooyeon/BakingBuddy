package com.coco.bakingbuddy.recommendation.service;

import com.coco.bakingbuddy.search.repository.RecentSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    private final RecentSearchRepository recentSearchRepository;
    /**
     public List<Product> recommendProductsBasedOnRecentSearches(User user) {
     List<RecentSearch> recentSearches = recentSearchRepository.findRecentSearchesByUser(user);
     // 각 검색어를 기반으로 상품 추천 로직을 구현합니다.
     // 예: 검색어와 연관된 태그나 카테고리를 분석하여 제품을 추천
     return recommendedProducts;
     }
     public List<Product> recommendProductsBasedOnTopRankingTerms() {
     List<RankingTermCache> topRankingTerms = rankingTermCacheQueryDslRepository.selectTop10RankingTerms();
     // 상위 랭킹 용어를 기반으로 추천 제품을 선택합니다.
     // 예: 각 용어와 관련된 제품을 데이터베이스에서 검색하여 추천
     return recommendedProducts;
     }
     public List<Product> recommendAdaptiveProducts(User user) {
     List<RecentSearch> userSearches = recentSearchRepository.findRecentSearchesByUser(user);
     List<RankingTermCache> topRankingTerms = rankingTermCacheQueryDslRepository.selectTop10RankingTerms();

     // 개인화 검색어와 상위 랭킹 검색어를 비교하여 추천 제품 선정
     return adaptiveRecommendedProducts;
     }
     **/


}
