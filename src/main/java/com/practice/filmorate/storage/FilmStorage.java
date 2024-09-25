package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film createFilm(Film film);
    Film update(Film film);
    Collection<Film> getAllFilms();
    Film getFilmById(int id);
}