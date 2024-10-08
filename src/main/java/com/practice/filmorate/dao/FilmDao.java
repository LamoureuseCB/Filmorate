package com.practice.filmorate.dao;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.RatingMpa;

import java.util.*;


public interface FilmDao {
    Film create(Film film);

    Film update(Film film);

    void deleteFromFilm(Film film);

    void deleteFromFilmGenre(Film film);

    Collection<Film> findAllFilms();

    List<Film> findFilmById(int id);

    Set<Genre> findGenres();

    Optional<Genre> findGenreById(int genreId);

    Set<Genre> findGenreByFilmId(int filmId);

    Set<RatingMpa> findRatingMpa();

    Optional<RatingMpa> findRatingMpaById(int ratingMpaId);
