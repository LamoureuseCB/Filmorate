package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        getUserById(user.getId());
        return userStorage.updateUser(user);
    }


    public Collection<User> findAllUsers() {
        return userStorage.findAllUsers();
    }


    private User getUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с данным ID"));
    }

    public void addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.add(friendId);
        user.setFriends(friends);

    }

    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.remove(friendId);
        user.setFriends(friends);

    }

    public List<User> getAllFriends(int userId) {
        User user = getUserById(userId);
        List<User> friendList = new ArrayList<>();
        Set<Integer> friends = user.getFriends();
        for (Integer friend : friends) {
            friendList.add(getUserById(friend));
        }
        return friendList;
    }

    public List<User> findCommonFriends(int userId, int otherId) {
        Set<Integer> userFriendsId = getUserById(userId).getFriends();
        Set<Integer> otherIdFriendsId = getUserById(otherId).getFriends();
        List<User> commonFriends = new ArrayList<>();
        for (Integer friend : userFriendsId) {
            if (otherIdFriendsId.contains(friend)) {
                commonFriends.add(getUserById(friend));
            }
        }
        return commonFriends;
    }
}