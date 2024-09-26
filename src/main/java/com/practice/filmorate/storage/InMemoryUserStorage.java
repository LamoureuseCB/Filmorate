package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    Map<Integer, User> users = new HashMap();
    private static int usersIdCounter = 1;

    @Override
    public User createUser(User user) {
        user.setId(usersIdCounter);
        users.put(usersIdCounter, user);
        log.info("Добавлен пользователь {}", user.getName());
        usersIdCounter++;
        return user;
    }

    @Override
    public User updateUser(User user) {
        users.put(user.getId(), user);
        log.info("Обновлен пользователь {}", user.getId());
        return user;
    }

    @Override
    public Collection<User> findAllUsers() {
        return users.values();

    }

    @Override
    public Optional<User> findUserById(int id) {
        return Optional.ofNullable(users.get(id));

    }
}

