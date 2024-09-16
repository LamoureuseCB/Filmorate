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
    private final Map<Integer, User> users = new HashMap<>();
    private static int usersIdCounter = 0;

    private void validateUser(User user) {
        LocalDate now = LocalDate.now();
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Адрес почты введён неверно");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не должен быть пустым и содержать пробелы");
        }
        if (user.getDateOfBirth() == null || user.getDateOfBirth().isAfter(now)) {
            throw new ValidationException("День рождения не может быть из будущего");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        validateUser(user);
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с данным ID уже существует");
        }

        user.setId(++usersIdCounter);
        users.put(user.getId(), user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с данным ID не существует");
        }
        users.put(user.getId(), user);
        log.info("Пользователь изменён: {}", user);
        return user;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }
}
