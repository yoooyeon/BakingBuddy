package com.coco.bakingbuddy.admin.dto.response;

import com.coco.bakingbuddy.admin.domain.UserAuthority;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectUserAuthorityResponseDto {
    private Long id;
    private SelectUserResponseDto user; // 승인 요청한 사용자

    private String roleType; // 승인 요청 역할/권한

    private boolean approval; // 승인 여부

    private String approvedBy; // 승인한 사람

    public static SelectUserAuthorityResponseDto fromEntity(UserAuthority entity) {
        return SelectUserAuthorityResponseDto.builder()
                .id(entity.getId())
                .user(SelectUserResponseDto.fromEntity(entity.getUser()))
                .roleType(entity.getRoleType().getDisplayName())
                .approval(entity.isApproval())
                .approvedBy(entity.getApprovalUser().getUsername())
                .build();
    }
}
