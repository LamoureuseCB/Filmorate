package com.practice.filmorate.dao;

import com.practice.filmorate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        String createQuery = "insert into users (name, email, login, birthday) values (?, ?, ?, ?);
        jdbcTemplate.update(createQuery, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String updateQuery = "update users set name = ?, email = ?, login = ?, birthday = ? where id = ?";
        jdbcTemplate.update(updateQuery, user.getName(), user.getEmail(), user.getLogin(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        String allUsersQuery = "select * from users";
        return jdbcTemplate.query(allUsersQuery, this::mapRow);
    }

    @Override
    public User getUserById(int id) {
        String byIdQuery = "select * from users where id = ?";
        return jdbcTemplate.query(byIdQuery, this::mapRow));
    }

    @Override
    public Collection<User> findAllFriends(int id) {
        String query = "select users. * from friends join users on friends.second_user_id = users.id where friends.first_user_id = ? and friends.friendship_status = 'дружба подтверждена'";
        return jdbcTemplate.query(query, this::mapRow));
    }


    @Override
    public void addFriend(int id, int friendId) {
        String addFriendQuery = "insert into friends (first_user_id, second_user_id, friendship_status) VALUES (?, ?, 'дружба подтверждена')";
        jdbcTemplate.update(addFriendQuery, id, friendId);
    }

    @Override
    public void removeFriend(int id, int friendId) {
        String removeFriendQuery = "delete from friends where first_user_id = ? and second_user_id = ?";
        jdbcTemplate.update(removeFriendQuery, id, friendId);
    }

    @Override
    public List<User> findCommonFriends(int id, int otherId) {
//       ????
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
