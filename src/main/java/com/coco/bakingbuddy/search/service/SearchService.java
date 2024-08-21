package com.coco.bakingbuddy.search.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.search.domain.SearchRecord;
import com.coco.bakingbuddy.search.repository.SearchRecordQueryDslRepository;
import com.coco.bakingbuddy.search.repository.SearchRecordRepository;
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
    private final SearchRecordQueryDslRepository searchRecordQueryDslRepository;
    private final SearchRecordRepository searchRecordRepository;

    public void addSearchRecord(Long userId, String term) {

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        SearchRecord existingSearch = searchRecordQueryDslRepository.selectSearchRecordsByUserIdAndTerm(userId, term);


        if (existingSearch != null) {
            // 검색어가 이미 존재하면 timestamp만 업데이트
            existingSearch.setTimestamp(LocalDateTime.now());
            userRepository.save(user); // 또는 별도로 searchRecords 저장소가 있다면 사용
        } else {
            // 현재 시간 기준으로 새로운 검색어 생성
            SearchRecord searchRecord = SearchRecord.create(term, user);
            SearchRecord save = searchRecordRepository.save(searchRecord);
            // 새로운 검색어 추가
            user.addSearchRecord(save);
            userRepository.save(user);
        }
    }

    public List<SearchRecord> getSearchRecords(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user.getSearchRecords();
    }

    public void clearSearchRecords(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.clearSearchRecords();
        userRepository.save(user);
    }
}
