package com.coco.bakingbuddy.search.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.search.domain.RecentSearch;
import com.coco.bakingbuddy.search.repository.RecentSearchQueryDslRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SearchService {
    private final UserRepository userRepository;
    private final RecentSearchQueryDslRepository recentSearchQueryDslRepository;
    public void addRecentSearch(Long userId, String term) {

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        RecentSearch existingSearch = recentSearchQueryDslRepository.selectRecentSearchByUserIdAndTerm(userId,term);


        if (existingSearch != null) {
            // 검색어가 이미 존재하면 timestamp만 업데이트
            existingSearch.setTimestamp(LocalDateTime.now());
            userRepository.save(user); // 또는 별도로 RecentSearch 저장소가 있다면 사용
        } else {
            // 새로운 검색어 추가
            user.addRecentSearch(term);
            userRepository.save(user);
        }
    }

    public List<RecentSearch> getRecentSearches(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getRecentSearches();
    }

    public void clearRecentSearches(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.clearRecentSearches();
        userRepository.save(user);
    }
}
