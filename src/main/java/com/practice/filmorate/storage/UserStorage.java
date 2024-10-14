package com.practice.filmorate.storage;

import com.practice.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
    User create(User user);

    User update(User user);

    Collection<User> getAllUsers();

    Optional<User> findUserById(int id);


}
