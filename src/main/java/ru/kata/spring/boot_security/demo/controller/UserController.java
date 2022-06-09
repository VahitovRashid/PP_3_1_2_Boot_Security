package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

        //Создание пользователей вручную
        //Создание пользователя с ролью ADMIN (Username = admin, Password = admin)
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(new Role("ROLE_ADMIN"));
        userService.saveUser(new User("admin", "admin", (byte) 20,
                "$2a$12$go8C1VfXwB0y3YIoqDNQc.Trii8EDze4QaIG1vVx9QyLzuElaCiM6", rolesAdmin));

        //Создание пользователя с ролью USER (Username = user1, Password = user1)
        Set<Role> rolesUser = new HashSet<>();
        rolesUser.add(new Role("ROLE_USER"));
        userService.saveUser(new User("user", "user", (byte) 21,
                "$2a$12$u.C3dU3YznMIX9tnbHIdfO0lX1ncoQ61rE/D.vBaiNJVBSQEoL6ce", rolesUser));

    }

    @GetMapping(value = "/admin/")
    String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/admin/add")
    String showAddUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping(value = "/admin/add")
    String saveUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/add";
        }
        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @GetMapping("/admin/edit/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}
