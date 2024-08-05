package com.coco.bakingbuddy.follow.domain;

import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower; // 유저(팔로우하는 사용자)

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed; // 에디터(팔로우되는 사용자)
}
