package com.practice.filmorate.storage;

import com.practice.filmorate.model.Genre;

import java.util.Optional;
import java.util.Set;

public interface GenreStorage {
    Optional<Genre> findGenreById ( int genreId);
    Set<Genre> findAll();
}

