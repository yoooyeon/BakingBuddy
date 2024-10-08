package com.coco.bakingbuddy.admin.service;

import com.coco.bakingbuddy.admin.domain.UserAuthority;
import com.coco.bakingbuddy.admin.dto.response.SelectUserAuthorityResponseDto;
import com.coco.bakingbuddy.admin.repository.UserAuthorityQueryDslRepository;
import com.coco.bakingbuddy.admin.repository.UserAuthorityRepository;
import com.coco.bakingbuddy.alarm.service.AlarmService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import com.coco.bakingbuddy.user.service.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.EXIST_AUTHORITY;
import static com.coco.bakingbuddy.global.error.ErrorCode.USER_AUTHORITY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {
    private final UserAuthorityRepository userAuthorityRepository;
    private final UserAuthorityQueryDslRepository userAuthorityQueryDslRepository;
    private final AlarmService alarmService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SelectUserAuthorityResponseDto> selectUserAuthorityRequests() {
        return userAuthorityQueryDslRepository.selectUserAuthorityRequests()
                .stream().map(SelectUserAuthorityResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveAuthority(Long id, User user) {
        // 현재 롤 확인
        UserAuthority userAuthority = userAuthorityRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_AUTHORITY_NOT_FOUND));
        RoleType savedRole = userAuthority.getUser().getRole();
        RoleType approveRole = userAuthority.getRoleType();
        User requestUser = userAuthority.getUser();

        // 승인처리
        if (savedRole.equals(approveRole)) {
            throw new CustomException(EXIST_AUTHORITY);
        }
        User saved = requestUser.updateRole(approveRole);
        userRepository.save(saved);
        userAuthority.updateApproval(user);
        // 승인 알림 처리
        alarmService.createAlarm(requestUser.getId(), approveRole.getDisplayName() + "권한 요청이 승인되었습니다.");

    }
}
