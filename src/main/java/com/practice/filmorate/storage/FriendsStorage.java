package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface FriendsStorage {
    Collection<User> findAllFriends(int id);
    void addFriend(int id, int friendId);
    void removeFriend(int id, int friendId);
    List<User> findCommonFriends(int id, int friendId);
}
