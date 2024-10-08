package com.coco.bakingbuddy.search.domain;


import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SearchRecord extends BaseTime {

    @Id
    @Column(name = "RECENT_SEARCH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public static SearchRecord create(String term, User user) {
        return SearchRecord.builder()
                .term(term)
                .timestamp(LocalDateTime.now())
                .user(user)
                .build();
    }
}
