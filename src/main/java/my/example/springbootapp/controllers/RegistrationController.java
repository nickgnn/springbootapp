package my.example.springbootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import my.example.springbootapp.entities.User;
import my.example.springbootapp.exception.DBException;
import my.example.springbootapp.service.UserService;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String regView() {
        return "registration";
    }

    @PostMapping("/registration")
    public String regUser(@RequestParam String username, @RequestParam String password, @RequestParam Integer age, Model model) throws DBException {
        if (userService.isExistsUser(username)) {
            model.addAttribute("message", "null");
            return "registration";
        }

        userService.addUser(new User(username, password, age));

        return "redirect:/login";
    }
}
