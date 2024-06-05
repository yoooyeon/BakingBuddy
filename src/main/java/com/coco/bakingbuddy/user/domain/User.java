package com.coco.bakingbuddy.user.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTime {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String profileImageUrl;
    // 관심 태그
//    private List<Tag> tags = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Directory> directories = new ArrayList<>();

    private boolean alarmYn; // 알림 설정


    public static User register(String username, String password) {
        // 사용자 객체 생성
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        // 사용자 등록 로직 추가 가능 (예: 이메일 확인, 초기 설정 등)
        return user;
    }


}
