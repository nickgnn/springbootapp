package my.example.springbootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import my.example.springbootapp.entities.User;
import my.example.springbootapp.exception.DBException;
import my.example.springbootapp.service.UserService;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String getAllUsers(Model model) throws DBException {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("name", currentUsername);
        model.addAttribute("isUser", false);
        model.addAttribute("isAdmin", true);

        model.addAttribute("users", userService.getAllUsers());

        return "admin";
    }

    @GetMapping("/admin/{id}")
    public String editUserView(@PathVariable("id") Long id, Model model) throws DBException {
        model.addAttribute("user", userService.getUserById(id));

        return "userEdit";
    }

    @PostMapping("/admin/{id}")
    public String editUser(@PathVariable("id") Long id, @RequestParam String username, @RequestParam String password, @RequestParam Integer age, @RequestParam(required = false) String role, Model model) throws DBException {
        User userEdited = new User(id, username, password, age, role);
        User userByName;

        if (userService.isExistsUser(username)) {
            userByName = userService.getUserByName(username);

            if (!userByName.getId().equals(userEdited.getId())) {
                return "redirect:/admin/{id}";
            }
        }

        userService.editUser(userEdited);

        return "redirect:/admin";
    }
}
