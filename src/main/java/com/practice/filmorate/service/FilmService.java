package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException();
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
        return getById(id);
    }

    public Film addLike(int filmId, int userId) {
        Film film = getById(filmId);
        Set<Integer> likes = film.getLikes();
        likes.add(userId);
        return film;
    }

    public void removeLike(int filmId, int userId) {
        Film film = getById(filmId);
        Set<Integer> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
        } else {
            throw new NotFoundException("Лайк от пользователя не найден");
        }
    }

    public Collection<Film> getTenPopularFilms() {
        Collection<Film> allFilms = filmStorage.findAll();
        return allFilms.stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(10)
                .toList();
    }

    private Film getById(int id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NotFoundException("Нет фильма с данным ID"));
    }
}