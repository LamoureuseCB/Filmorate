package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;


    public Film create(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("Дата не должна быть ранее 28 декабря 1895");
        }
        return filmStorage.create(film);
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

//    public Film addLike(int filmId, int userId) {
//        Film film = getById(filmId);
//        if (userStorage.findUserById(userId).isEmpty()) {
//            throw new NotFoundException("Пользователь не найден");
//        }
//        Set<Integer> likes = film.getLikes();
//        if (!likes.contains(userId)) {
//            likes.add(userId);
//        } else {
//            throw new RuntimeException("Лайк от этого пользователя уже существует");
//        }
//        return likeStorage.addLike(filmId, userId);
//    }
//
//    public void removeLike(int filmId, int userId) {
//        Film film = getById(filmId);
//        if (userStorage.findUserById(userId).isEmpty()) {
//            throw new NotFoundException("Пользователь не найден");
//        }
//        if (film.getId() != filmId) {
//            throw new NotFoundException("Фильм не найден");
//        }
//        Set<Integer> likes = film.getLikes();
//        if (likes.contains(userId)) {
//            likes.remove(userId);
//        } else {
//            throw new NotFoundException("Лайк от пользователя не найден");
//        }
//        likeStorage.removeLike(filmId, userId);
//    }
    public void addLike(int id, int userId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден.");
        }
        likeStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        if (userStorage.findUserById(id).isEmpty() || userStorage.findUserById(userId).isEmpty()) {
            throw new NotFoundException("Пользователь не найден");
        }
        likeStorage.removeLike(id, userId);
    }


    public Collection<Film> getTenPopularFilms(int count) {
        return filmStorage.getTenPopularFilms(count);
    }

    private Film getById(int id) {
        return filmStorage.findById(id).orElseThrow(() -> new NotFoundException("Нет фильма с данным ID"));
    }

    public Set<Mpa> findAllMpa() {
        return mpaStorage.getAllMpa();
    }

    public Mpa findMpaById(int id) {
        return mpaStorage.findMpaById(id).orElseThrow(() -> new NotFoundException("Рейтинг MPA не найден"));
    }

    public Set<Genre> findAllGenres() {
        return genreStorage.findAll();
    }

    public Genre findGenreById(int id) {
        return genreStorage.findGenreById(id).orElseThrow(() -> new NotFoundException("Жанр не найден"));
    }

}