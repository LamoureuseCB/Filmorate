package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FriendsStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Qualifier("friendsDbStorage")
public class FriendsDbStorage implements FriendsStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;
    private final String select = """
            select * from friends
                           join users on friends.second_user_id = users.id
            """;

    private final String selectCommonFriends = """
            select *
            from (select users *
                  from friends as f1
                           join friends as f2 on f1.second_user_id = f2.second_user_id
                           join users on f1.second_user_id = users.id
                  where f1.first_user_id = ?
                    and f2.first_user_id = ?
                    and f1.friendship_status = 'дружба подтверждена'
                    and f2.friendship_status = 'дружба подтверждена'
            """;

    @Override
    public Collection<User> findAllFriends(int userId) {
        String allFriendsQuery = select + "where friends.first_user_id = ?";
        return jdbcTemplate.query(allFriendsQuery, this::mapRow, userId);
    }

    @Override
    public void addFriend(int firstUserId, int secondUserId) {
        String addFriendQuery = "insert into  friends (first_user_id, second_user_id, friendship_status) values (?, ?, 'дружба подтверждена')";
        jdbcTemplate.update(addFriendQuery, firstUserId, secondUserId);
    }

    @Override
    public void removeFriend(int firstUserId, int secondUserId) {
        String removeFriendQuery = "delete from friends where first_user_id = ? and second_user_id = ?";
        jdbcTemplate.update(removeFriendQuery, firstUserId, secondUserId);
    }

    @Override
    public List<User> findCommonFriends(int firstUserId, int secondUserId) {
        String commonFriendsQuery = selectCommonFriends;
        return jdbcTemplate.query(commonFriendsQuery, this::mapRow, firstUserId, secondUserId);
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return userDbStorage.mapRow(rs, rowNum);
    }
}
