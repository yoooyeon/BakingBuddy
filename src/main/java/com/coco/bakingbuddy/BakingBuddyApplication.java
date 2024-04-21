package com.coco.bakingbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableAspectJAutoProxy
@EnableJpaAuditing
@SpringBootApplication
public class BakingBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakingBuddyApplication.class, args);
    }

}
