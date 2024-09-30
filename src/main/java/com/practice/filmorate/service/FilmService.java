package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.UserStorage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film createFilm(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("Дата не должна быть ранее 28 декабря 1895");
        }
        return filmStorage.createFilm(film);
    }

    public Film update(Film film) {
        getById(film.getId());
        return filmStorage.update(film);
    }

    public Collection<Film> getAllFilms() {
        return filmStorage.findAll();
    }

    public Film getFilmById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Нет фильма с данным ID"));
    }

    public Film addLike(int filmId, int userId) {
        Film film = getById(filmId);
        if (userStorage.findUserById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        Set<Integer> likes = film.getLikes();
        if (!likes.contains(userId)) {
            likes.add(userId);
        } else {
            throw new RuntimeException("Лайк от этого пользователя уже существует");
        }
        return film;
    }

    public void removeLike(int filmId, int userId) {
        Film film = getById(filmId);
        if (userStorage.findUserById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (film.getId() != filmId) {
            throw new NotFoundException("Фильм не найден");
        }
        Set<Integer> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
        } else {
            throw new NotFoundException("Лайк от пользователя не найден");
        }
    }

    public Collection<Film> getTenPopularFilms(int count) {
        Collection<Film> allFilms = filmStorage.findAll();
        return allFilms.stream().sorted((film1, film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size())).limit(count).toList();
    }

    private Film getById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Нет фильма с данным ID"));
    }
}