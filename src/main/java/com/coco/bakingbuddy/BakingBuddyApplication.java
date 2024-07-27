package com.coco.bakingbuddy;

import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.user.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableOAuth2Client
@EnableScheduling
@EnableCaching
@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class BakingBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakingBuddyApplication.class, args);
    }

    @Bean
    @Profile("dev")
    public TestDataInit testDataInit(RecipeRepository recipeRepository, UserRepository userRepository, DirectoryRepository directoryRepository) {
        return new TestDataInit(recipeRepository, userRepository, directoryRepository);
    }
}
