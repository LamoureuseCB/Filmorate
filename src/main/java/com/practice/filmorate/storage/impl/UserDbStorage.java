package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Qualifier("userDbStorage")
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
        String updateQuery= "update users set email = ?, login = ?, name = ?, birthday = ? WHERE id = ?";
        int updated = jdbcTemplate.update(updateQuery,user.getEmail(),user.getLogin(),user.getName(), user.getBirthday(), user.getId());
        if (updated == 0) {
            throw new NotFoundException("Пользователь с ID " + user.getId() + " не найден");
        }
        return findUserById(user.getId()).orElseThrow(() ->
                new NotFoundException("Пользователь с ID " + user.getId() + " не найден после обновления"));
    }


    @Override
    public Collection<User> findAllUsers() {
        String allUsersQuery = "select * from users";
        return jdbcTemplate.query(allUsersQuery, this::mapRow);
    }

    @Override
    public Optional<User> findUserById(int id) {
        String byIdQuery = "select * from users where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(byIdQuery, this::mapRow, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String eMail = rs.getString("email");
        String login = rs.getString("login");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, name, eMail, login, birthday);

    }

}


