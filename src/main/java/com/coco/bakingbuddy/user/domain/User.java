package com.coco.bakingbuddy.user.domain;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.like.domain.Like;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.search.domain.RecentSearch;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


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
    private String nickname;
    @Column(nullable = false)
    private String username;
    private String password;
    private String profileImageUrl;
    private String role;
    private String picture;
    private String profile;
    private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likedRecipes = new HashSet<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecentSearch> recentSearches = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Directory> directories = new ArrayList<>();

    @JsonIgnore
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

    public void updateUsername(String username) {
        this.username = username;
    }
    // 최대 최근 검색어 수
    private static final int MAX_RECENT_SEARCHES = 10;

    public void addRecentSearch(RecentSearch recentSearch) {
        recentSearches.add(recentSearch);
        recentSearch.setUser(this); // 연관 관계 설정
    }


    public void clearRecentSearches() {
        this.recentSearches.clear();
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

