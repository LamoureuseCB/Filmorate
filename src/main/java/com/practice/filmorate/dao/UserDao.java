package com.practice.filmorate.dao;

import com.practice.filmorate.model.User;


import java.util.Collection;
import java.util.List;

public interface UserDao {
    User createUser(User user);

    User updateUser(User user);

    Collection<User> getAllUsers();

    User getUserById(int id);

    Collection<User> findAllFriends(int id);

    void addFriend(int id, int friendId);

    void removeFriend(int id, int friendId);

    List<User> findCommonFriends(int id, int otherId);
}
