package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;

public interface FriendsStorage {
    void addFriend(int id, int friendId);
    void removeFriend(int id, int friendId);
    Collection<User> findAllFriends(int id);
    Collection<User> findCommonFriends(int id, int otherId);
}
