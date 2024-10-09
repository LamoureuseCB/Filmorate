package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FriendsStorage;
import com.practice.filmorate.storage.UserStorage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Getter

public class UserService {
    private final UserStorage userStorage;


    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        return userStorage.create(user);
    }

    public User update(User user) {
        getUserById(user.getId());
        return userStorage.update(user);
    }


    public Collection<User> getAllUsers() {
        return userStorage.findAllUsers();
    }


    public User getUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с данным ID"));
    }

    public void addFriend(int userId, int friendId) {
        User user = getUserById(userId);
        user.getFriends().add(friendId);

        User friend = getUserById(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(int userId, int friendId) {
        User user = getUserById(userId);
        user.getFriends().remove(friendId);

        User friend = getUserById(friendId);
        friend.getFriends().remove(userId);
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