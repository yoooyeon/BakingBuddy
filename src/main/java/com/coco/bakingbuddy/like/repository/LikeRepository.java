package com.coco.bakingbuddy.like.repository;

import com.coco.bakingbuddy.like.domain.Like;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUser(User user);

    Optional<Like> findByUser(Recipe recipe);

    Optional<Like> findByRecipeAndUser(Recipe recipe, User user);
}
