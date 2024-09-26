package com.practice.filmorate.service;

import com.practice.filmorate.model.User;
import com.practice.filmorate.storage.UserStorage;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Getter
public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.add(friendId);
        user.setFriends(friends);

    }

    public void removeFriend(int userId, int friendId) {
        User user = userStorage.getUserById(userId);
        Set<Integer> friends = user.getFriends();
        friends.remove(friendId);
        user.setFriends(friends);

    }

    public List<User> getAllFriends(int userId) {
        User user = userStorage.getUserById(userId);
        List<User> friendList = new ArrayList<>();
        Set<Integer> friends = user.getFriends();
        for (Integer friend : friends) {
            friendList.add(userStorage.getUserById(friend));
        }
        return friendList;
    }

    public List<User> findCommonFriends(int userId, int otherId) {
        Set<Integer> userFriendsId = userStorage.getUserById(userId).getFriends();
        Set<Integer> otherIdFriendsId = userStorage.getUserById(otherId).getFriends();
        List<User> commonFriends = new ArrayList<>();
        for (Integer friend : userFriendsId) {
            if (otherIdFriendsId.contains(friend)) {
                commonFriends.add(userStorage.getUserById(friend));
            }
        }
        return commonFriends;
    }
}