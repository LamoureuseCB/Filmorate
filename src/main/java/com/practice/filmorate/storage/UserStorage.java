package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    User createUser(User user);
    User updateUser(User user);
    Collection<User> findAllUsers();
    Optional<User> findUserById(int id);


}
