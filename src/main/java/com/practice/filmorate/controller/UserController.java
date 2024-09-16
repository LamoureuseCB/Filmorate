package com.practice.filmorate.controller;

import com.practice.filmorate.model.User;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController

@RequestMapping("/users")
public class UserController {
    Map<Integer, User> users = new HashMap();
    private static int usersIdCounter = 1;


    private void validUser(User user) {
        LocalDate now = LocalDate.now();
        if (user.getEmail() == null || user.getEmail().isEmpty() || user.getEmail().isBlank()
                || !user.getEmail().contains("@")) {
            throw new ValidationException("Адрес почты введён неверно");
        } else if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().isBlank()
                || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и содержать пробелы");
        } else if (user.getDateOfBirth().isAfter(now)) {
            throw new ValidationException("День рождения не может быть из будущего :)");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping
    public User createUser(User user) {
        validUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Нет пользователя с данным ID");
        }
        validUser(user);
        users.put(user.getId(), user);
        log.info("В коллекции обновлен пользователь {}", user.getName());
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }


}
