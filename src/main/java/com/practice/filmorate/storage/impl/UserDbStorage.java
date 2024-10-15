package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.mappers.UserMapper;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
@RequiredArgsConstructor

public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("login", user.getLogin());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("birthday", user.getBirthday());

        Number generatedId = insert.executeAndReturnKey(map);
        user.setId(generatedId.intValue());
        return user;
    }

    @Override
    public User update(User user) {
        String updateQuery = "update users set email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
        int updated = jdbcTemplate.update(updateQuery, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (updated == 0) {
            throw new NotFoundException("Пользователь с ID " + user.getId() + " не найден");
        }
        return findUserById(user.getId()).orElseThrow(() ->
                new NotFoundException("Пользователь с ID " + user.getId() + " не найден после обновления"));
    }


    @Override
    public List<User> getAllUsers() {
        String allUsersQuery = "select * from users";
        return jdbcTemplate.query(allUsersQuery, new UserMapper());
    }

    @Override
    public Optional<User> findUserById(int id) {
        String byIdQuery = "select * from users where id = ?";
        return jdbcTemplate.query(byIdQuery, new UserMapper(), id).stream().findFirst();
    }

}



