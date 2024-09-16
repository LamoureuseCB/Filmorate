package com.practice.filmorate;

import com.practice.filmorate.model.User;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public class UserTest {
    private static final Validator validator = buildDefaultValidatorFactory().getValidator();

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("Kate");
        user.setEmail("kate@mail.ru");
        user.setLogin("firstUser");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Добавлен пользователь: " + user.getName();
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setName("Kate");
        user.setEmail("kate@mail.ru");
        user.setLogin("firstUser");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));


        user.setName("UpdatedKate");
        String expected = "Обновлен пользователь: " + user.getName();
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailEmailEmpty() {
        User user = new User();
        user.setLogin("firstUser");
        user.setName("Kate");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Электронная почта не может быть пустой";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testEmailWrongFormat() {
        User user = new User();
        user.setEmail("kate_mail.ru");
        user.setLogin("firstUser");
        user.setName("Kate");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Электронная почта должна содержать символ @";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailLogin() {
        User user = new User();
        user.setEmail("kate@mail.ru");
        user.setName("Kate");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Логин не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailLoginWithSpaces() {
        User user = new User();
        user.setEmail("kate@mail.ru");
        user.setLogin("first User");
        user.setName("Kate");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Логин не может содержать пробелы";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailBirthday() {
        User user = new User();
        user.setEmail("kate@mail.ru");
        user.setLogin("firstUser");
        user.setName("Kate");

        String expected = "Дата рождения не может быть пустой";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailEmptyName() {
        User user = new User();
        user.setEmail("kate@mail.ru");
        user.setLogin("firstUser");
        user.setDateOfBirth(LocalDate.of(1970, 1, 6));

        String expected = "Имя не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailBirthdayFuture() {
        User user = new User();
        user.setEmail("kate@mail.ru");
        user.setLogin("firstUser");
        user.setName("Kate");
        user.setDateOfBirth(LocalDate.of(2500, 1, 6));

        String expected = "Дата рождения должна быть не позднее текущей даты";
        String actual = validateAndGetFirstMessageTemplate(user);

        Assertions.assertEquals(expected, actual);
    }

    protected String validateAndGetFirstMessageTemplate(User obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .map(violation -> violation.getConstraintDescriptor().getMessageTemplate())
                .orElse("No validation errors");
    }
}
