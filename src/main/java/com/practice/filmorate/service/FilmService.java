package com.practice.filmorate.service;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.storage.FilmStorage;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
public class FilmService {
    private final FilmStorage filmStorage;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        Set<Integer> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);

        return film;
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        Set<Integer> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
            film.setLikes(likes);
        } else {
            throw new NotFoundException("Лайк от пользователя не найден");
        }
    }

    public Collection<Film> getTenPopularFilms() {
        Collection<Film> allFilms = filmStorage.getAllFilms();
        return allFilms.stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size()).reversed())
                .limit(10)
                .toList();
    }
}
