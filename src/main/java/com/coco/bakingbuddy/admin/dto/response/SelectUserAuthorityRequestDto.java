package com.coco.bakingbuddy.admin.dto.response;

import com.coco.bakingbuddy.admin.domain.UserAuthority;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.RoleType;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectUserAuthorityRequestDto {
    private SelectUserResponseDto user; // 승인 요청한 사용자

    private RoleType roleType; // 승인 요청 역할/권한

    private boolean approval; // 승인 여부

    public static SelectUserAuthorityRequestDto fromEntity(UserAuthority entity) {
        return SelectUserAuthorityRequestDto.builder()
                .user(SelectUserResponseDto.fromEntity(entity.getUser()))
                .roleType(entity.getRoleType())
                .approval(entity.isApproval())
                .build();
    }
}
