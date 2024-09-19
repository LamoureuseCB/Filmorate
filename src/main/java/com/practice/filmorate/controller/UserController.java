package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    Map<Integer, User> users = new HashMap();
    private static int usersIdCounter = 1;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(usersIdCounter);
        users.put(usersIdCounter, user);
        log.info("Добавлен пользователь {}", user.getName());
        usersIdCounter++;
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с таким ID не существует");
        }
        users.put(user.getId(), user);
        log.info("Обновлен пользователь {}", user.getId());
        return user;
    }

    @GetMapping
    public Collection< User> getUsers() {
        return users.values();
    }

}
