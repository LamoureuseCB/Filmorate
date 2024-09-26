package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    Map<Integer, User> users = new HashMap();
    private static int usersIdCounter = 1;
    private static final String USER_ID_MESSAGE = "Пользователь по ID не найден";


    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        user.setId(usersIdCounter);
        users.put(usersIdCounter, user);
        log.info("Добавлен пользователь {}", user.getName());
        usersIdCounter++;
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователя с таким ID не существует");
        }
        users.put(user.getId(), user);
        log.info("Обновлен пользователь {}", user.getId());
        return user;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();

    }

    @Override
    public User getUserById(int id) {
        if(users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new NotFoundException(USER_ID_MESSAGE);
        }
    }
}

