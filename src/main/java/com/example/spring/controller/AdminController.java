package com.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.spring.model.User;
import com.example.spring.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String user(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user1", userService.getAuthorized());
        return "all-users";
    }

    @GetMapping("user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("user1", userService.getAuthorized());
        return "user-admin";
    }

    @GetMapping("user/authorized")
    public String getAuthorized(Model model) {
        model.addAttribute("user1", userService.getAuthorized());
        return "user-admin";
    }

    @GetMapping("/create-user")
    public String addUserForm(Model model) {
        model.addAttribute("user1", userService.getAuthorized());
        return "/create-user";
    }

    @PostMapping("create-user")
    public String addUser(@RequestParam String name,
                          @RequestParam int age,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {
        userService.save(name, email, age, password, role);
        return "redirect:/admin";
    }

    @GetMapping("/update-user/{id}")
    public String updateUserForm(@PathVariable("id") int id, Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("user1", userService.getAuthorized());
        return "update-user";
    }

    @PutMapping("update-user")
    public String updateUser(@RequestParam int id,
                             @RequestParam String name,
                             @RequestParam int age,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String role) {
        userService.updateUser(id, name, email, age, password, role);
        return "redirect:/admin";
    }

    @GetMapping("/delete-user/{id}")
    public String deleteUserForm(@PathVariable("id") int id, Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("user1", userService.getAuthorized());
        return "delete-user";
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }
}
