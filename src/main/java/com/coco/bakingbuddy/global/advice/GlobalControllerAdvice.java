package com.coco.bakingbuddy.global.advice;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(@AuthenticationPrincipal User user, Model model) {
        if (user != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", user);
        } else {
            model.addAttribute("loggedIn", false);
        }
    }
}
