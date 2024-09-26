package com.practice.filmorate;


import com.practice.filmorate.model.User;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;


import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public class UserTest {
    private static final Validator validator = buildDefaultValidatorFactory().getValidator();
    private User user;

    @BeforeEach
    void createdUser() {
        user = new User();
        user.setName("Kate");
        user.setEmail("kate@gmail.com");
        user.setLogin("kate");
        user.setBirthday(LocalDate.of(1970, Month.JANUARY, 1));

    }


    @Test
    void testFailEmailEmpty() {
        user.setEmail("");
        String expected = "Электронная почта не может быть пустой";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testEmailWrongFormat() {
        user.setEmail("kate_mail.ru");

        String expected = "Электронная почта должна содержать символ @";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailLogin() {
        user.setLogin("");

        String expected = "Логин не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void testCreateFailLoginWithSpaces() {
        user.setLogin("k  a  t  e");

        String expected = "Логин не может содержать пробелы";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailBirthday() {
        user.setBirthday(LocalDate.of(1970, Month.JANUARY, 1));

        String expected = "Дата рождения не может быть пустой";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailEmptyName() {
        user.setName("");

        String expected = "Имя не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailBirthdayFuture() {
        user.setBirthday(LocalDate.of(2500, 1, 6));

        String expected = "Дата рождения должна быть не позднее текущей даты";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(User obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}