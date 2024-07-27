package com.coco.bakingbuddy.auth.advice;

import com.coco.bakingbuddy.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {
    public void addAttributes(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("loggedIn", false);
            model.addAttribute("user", null);
        }
    }
}
