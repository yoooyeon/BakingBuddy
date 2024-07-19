package com.coco.bakingbuddy.user.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

}
