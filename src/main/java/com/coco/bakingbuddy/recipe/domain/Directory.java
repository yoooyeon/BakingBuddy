package com.coco.bakingbuddy.recipe.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Directory extends BaseTime {
    @Column(name = "DIRECTORY_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean useYn;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Builder.Default
    @OneToMany(mappedBy = "directory", fetch = FetchType.LAZY)
    private List<Recipe> recipes = new ArrayList<>();

    public void delete() {
        this.useYn = false;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
