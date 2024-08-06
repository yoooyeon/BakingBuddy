package com.coco.bakingbuddy.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FollowSummaryResponseDto {
    private int followerCnt;
    private int followedCnt;
    private List<FollowResponseDto> followers;
    private List<FollowResponseDto> followedUsers;
}
