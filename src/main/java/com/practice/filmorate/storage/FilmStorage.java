package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);
    Film update(Film film);
    Collection<Film> findAll();
    Optional<Film> findById(int id);
    Collection<Film>getTenPopularFilms(int count);
}