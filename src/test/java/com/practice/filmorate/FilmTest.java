package com.practice.filmorate;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.practice.filmorate.model.Film;

import java.time.LocalDate;

public class FilmTest {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testCreateFailName() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);
        film.setDescription("Научно-фантастический боевик");
        film.setReleaseDate(LocalDate.of(2013, 4, 11));
        film.setDuration(124);

        String expected = "Название не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailLongName() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);
        String longName = "События фантастического фильма Обливион разворачиваются в далеком будущем, где наша планета Земля подверглась вторжению.События фантастического фильма Обливион разворачиваются в далеком будущем, где наша планета Земля подверглась вторжению";
        film.setName(longName);
        film.setDescription("Научно-фантастический боевик");
        film.setReleaseDate(LocalDate.of(2013, 04, 11));
        film.setDuration(124);

        String expected = "Название должно быть не длиннее 200 символов";
        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailDescription() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);

        film.setName("Обливион");
        film.setDescription("");
        film.setReleaseDate(LocalDate.of(2013, 4, 11));
        film.setDuration(124);

        String expected = "Описание не может быть пустым";
        String actual = validateAndGetFirstMessageTemplate(film);


        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCreateFailReleaseDate() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);
        film.setName("Обливион");
        film.setDescription("Научно-фантастический боевик");
        film.setReleaseDate(LocalDate.of(3013, 4, 11));
        film.setDuration(124);

        String expected = "Дата релиза должна быть не раньше 28 декабря 1895 года";
        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailDuration() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);
        film.setName("Обливион");
        film.setDescription("Научно-фантастический боевик");
        film.setReleaseDate(LocalDate.of(2013, 4, 11));
        film.setDuration(0);

        String expected = "Продолжительность фильма должна быть положительной";
        String actual = validateAndGetFirstMessageTemplate(film);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailNegativeDuration() {
        Film film = new Film(id, title, description, releaseYear, duration, ratingMpa);
        film.setName("Обливион");
        film.setDescription("Научно-фантастический боевик");
        film.setReleaseDate(LocalDate.of(2013, 4, 11));
        film.setDuration(-124);

        String expected = "Продолжительность фильма должна быть положительной";
        String actual = validateAndGetFirstMessageTemplate(film);


        Assertions.assertEquals(expected, actual);
    }


    protected String validateAndGetFirstMessageTemplate(Film obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .orElseThrow()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}