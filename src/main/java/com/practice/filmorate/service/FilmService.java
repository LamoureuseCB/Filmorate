package com.practice.filmorate.service;

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

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        Set<Integer> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);

    }

    public void removeLike(int filmId, int userId) {
        Film film = filmStorage.getFilmById(filmId);
        Set<Integer> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
            film.setLikes(likes);
        }
    }
    public List<Film> getTenPopularFilms(){
        Collection<Film> allFilms= filmStorage.getAllFilms();
        List<Film> tenPopularFilms = new ArrayList<>();
//        Comparator<Film> ????
        return tenPopularFilms;
    }
}
//Создайте FilmService, который будет отвечать
// за операции с фильмами, — добавление и удаление лайка,
// вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.