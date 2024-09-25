package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User createUser(User user);
    User updateUser(User user);
    Collection<User> getUsers();
    User getUserById(int id);


}
