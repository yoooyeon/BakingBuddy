package com.coco.bakingbuddy;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final DirectoryRepository directoryRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");
        User user = userRepository.save(User.builder()
                .nickname("NJ")
                .password("$2a$12$3gJohU5nBOqzpxQ.we6MyOQZuetlqIyfGtq7S0cLnMZ7g/j0tjh2q")
                .username("NJ")
                .profileImageUrl("https://storage.googleapis.com/baking-buddy-bucket/RecipeProfile/2dc44f8d-061e-4a3b-8c10-9d5fe455c800_NewJeans.JPG")
                .build());
        Directory dir = directoryRepository.save(Directory.builder()
                .user(user)
                .name("DIR1")
                .build());
        recipeRepository.save(Recipe.builder()
                .name("RECIPE1")
                .directory(dir)
                .level("Easy")
                .time(10)
                .recipeImageUrl("https://storage.googleapis.com/baking-buddy-bucket/RecipeProfile/e59e54e0-ebe5-4731-8458-beb058d6cfad_bread.jpg")
                .build());
    }

}
