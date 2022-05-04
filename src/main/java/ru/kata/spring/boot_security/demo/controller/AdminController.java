package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.getUsers());
        return "admin";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String addUser (@ModelAttribute("user") User user,
                           @RequestParam(value = "rolesList") String [] roles,
                           @ModelAttribute("pass") String pass) {
        userService.save(user, roles, pass);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String  edit (Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findUser(id));
        return "update";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") User user, @PathVariable("id") int id,
                          @RequestParam(value = "rolesList") String [] roles,
                          @ModelAttribute("pass") String pass) {
        userService.update(user, id, roles, pass);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
