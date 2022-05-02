package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public String user(ModelMap model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
    @GetMapping("/admin")
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("/admin")
    public String addUser (@ModelAttribute("user") User user,
                           @RequestParam(value = "rolesList") String [] roles,
                           @ModelAttribute("pass") String pass) {

        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(Arrays.stream(roles)
                .map(role -> roleService.findByRole(role))
                .collect(Collectors.toList()));
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/update")
    public String  edit (Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findUser(id));
        return "update";
    }

    @PatchMapping("/admin/{id}")
    public String update (@ModelAttribute("user") User user, @PathVariable("id") int id,
                          @RequestParam(value = "rolesList") String [] roles,
                          @ModelAttribute("pass") String pass) {

        user.setPassword(passwordEncoder.encode(pass));
        user.setRoles(Arrays.stream(roles)
                .map(role -> roleService.findByRole(role))
                .collect(Collectors.toList()));
        userService.update(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
