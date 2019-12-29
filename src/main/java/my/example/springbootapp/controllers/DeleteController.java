package my.example.springbootapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import my.example.springbootapp.exception.DBException;
import my.example.springbootapp.service.UserService;

@Controller
public class DeleteController {
    @Autowired
    UserService userService;

    @PostMapping("/del")
    public String delete(@RequestParam(value = "id") Long id) throws DBException {
        userService.deleteUserById(id);

        return "redirect:/admin";
    }
}
