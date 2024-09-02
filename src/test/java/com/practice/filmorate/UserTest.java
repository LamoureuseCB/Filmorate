package com.practice.filmorate;

import com.practice.filmorate.controller.UserController;
import com.practice.filmorate.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserTest {
    UserController userController = new UserController();
    @Test
    public void testAddUser() {

        User user = new User();
        user.setEmail("email_mail");
        user.setLogin("user1");
        user.setDateOfBirth(LocalDate.of(2020,2,20));
        userController.addUser(user);


    }

}
