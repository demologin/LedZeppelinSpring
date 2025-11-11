package com.javarush.led.lesson07.controller;

import com.javarush.led.lesson07.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final List<User> users = new ArrayList<>(){
        {
            this.add(new User(1L,"Test","test", "USER"));
        }
    };

    @GetMapping("/list")
    public String getUserList(Model model) {
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/new")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/add")
    public String addUser(
            @ModelAttribute("user") User user,
            @RequestParam("login") String login) {
        users.add(user);
        return "redirect:/user/list";
    }





}
