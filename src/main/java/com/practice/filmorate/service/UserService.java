package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.FriendsStorage;
import com.practice.filmorate.storage.UserStorage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Getter
@AllArgsConstructor

public class UserService {
    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;


    public User create(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getName());
        }
        return userStorage.create(user);
    }

    public User update(User user) {
        getUserById(user.getId());
        return userStorage.update(user);
    }


    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Нет пользователя с данным ID"));
    }

    public void addFriend(int id, int friendId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (id < 0 || friendId < 0) {
            throw new NotFoundException("Пользователь не найден.");
        }
        friendsStorage.addFriend(id, friendId);

    }

    public void removeFriend(int id, int friendId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(friendId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        friendsStorage.removeFriend(id, friendId);
    }

    public Collection<User> findAllFriends(int id) {
        if (userStorage.findUserById(id).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return friendsStorage.findAllFriends(id);
    }

    public Collection<User> getCommonFriends(int id, int otherId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(otherId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        return friendsStorage.findCommonFriends(id, otherId);
    }

}




