package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    List<User> users = new ArrayList<>();
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        users.add(user);
        log.info("Добавлен пользователь {}", user.getName());
        return user;
    }
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        users.remove(user.getId());
        users.add(user);
        log.info("Обновлен пользователь {}", user.getId());
        return user;
    }
    @GetMapping
    public List<User> getUsers() {
        return users;
    }


}
