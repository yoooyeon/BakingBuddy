package com.coco.bakingbuddy.user.service;

import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
                });
    }


}
