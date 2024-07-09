package com.coco.bakingbuddy.user.domain;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTime implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)

    private String username;
    private String password;
    private String profileImageUrl;
    // 관심 태그
//    private List<Tag> tags = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Directory> directories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Alarm> alarms = new ArrayList<>();

    private boolean alarmYn; // 알림 설정

    // 권한 관련 작업을 하기 위한 role return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
//        collections.add(() -> {
//            return user.getRole().name();
//        });

        return collections;
    }


    // 계정이 만료 되었는지 (true: 만료X)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 (true: 만료X)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public static User register(String username, String password) {
        // 사용자 객체 생성
        User user = User.builder()
                .username(username)
                .password(password)
                .recipes(new ArrayList<>())
                .directories(new ArrayList<>())
                .alarms(new ArrayList<>())
                .build();

        // 사용자 등록 로직 추가 가능 (예: 이메일 확인, 초기 설정 등)
        return user;
    }


    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfile(String uploadPath) {
        this.profileImageUrl = uploadPath;
    }


}
