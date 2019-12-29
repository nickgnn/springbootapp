package my.example.springbootapp.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user")
    public String user(Model model) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        String role = (String) SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(s -> (s).getAuthority())
                .toArray()[0];

        model.addAttribute("name", currentUsername);

        if (role.contains("ADMIN")) {
            model.addAttribute("isAdmin", true);
        } else {
            model.addAttribute("isAdmin", false);
        }

        return "user";
    }
}
