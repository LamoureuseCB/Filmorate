package com.practice.filmorate.storage.impl;


import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FriendsStorage;

import com.practice.filmorate.storage.mappers.UserMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@Component
@RequiredArgsConstructor

public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public void addFriend(int userId, int friendId) {
        String addFriendQuery = "insert into  friends (first_user_id, second_user_id ) values (?, ?)";
        jdbcTemplate.update(addFriendQuery, userId, friendId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        String removeFriendQuery = "delete from friends where first_user_id = ? and second_user_id = ?";
        jdbcTemplate.update(removeFriendQuery, userId, friendId);
    }

    @Override
    public List<User> findAllFriends(int id) {
        String sql = "select * from users  " +
                "join friends  on (users.id = friends.second_user_id) " +
                "where friends.first_user_id = ?";
        return jdbcTemplate.query(sql, new UserMapper(), id);
    }

    @Override
    public Collection<User> findCommonFriends(int first_user_id, int second_user_id) {
        String sql = "select * from users u  " +
                "join friends f1 on (u.id = f1.second_user_id) " +
                "join friends f2 on (u.id = f2.second_user_id) " +
                "where f1.first_user_id = ? and f2.first_user_id = ? " +
                "and f1.second_user_id = f2.second_user_id";
        return jdbcTemplate.query(sql, new UserMapper(), first_user_id, second_user_id);

    }

}
