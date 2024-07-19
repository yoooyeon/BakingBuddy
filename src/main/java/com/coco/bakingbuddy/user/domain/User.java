package com.coco.bakingbuddy.user.domain;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.search.domain.RecentSearch;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Builder
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTime implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nickname;
    @Column(nullable = false)
    private String username;
    private String password;
    private String profileImageUrl;
    //    @Enumerated(EnumType.STRING)
    private String role;
    private String picture;
    private String profile;
    private String email;
    // 관심 태그
//    private List<Tag> tags = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecentSearch> recentSearches = new ArrayList<>();

    // 기존 메서드들

    public void addRecentSearch(String term) {
        RecentSearch recentSearch = RecentSearch.create(term, this);
        this.recentSearches.add(recentSearch);
    }

    public void clearRecentSearches() {
        this.recentSearches.clear();
    }
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
    private boolean activated;
    private String provider; //어떤 OAuth인지(google, naver 등)
    private String provideId; // 해당 OAuth 의 key(id)


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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

