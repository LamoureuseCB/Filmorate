package com.practice.filmorate.storage;

import com.practice.filmorate.model.Film;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    public int idCounter = 1;

    @Override
    public Film createFilm(Film film) {
        film.setId(idCounter);
        films.put(idCounter, film);
        log.info("В коллекцию добавлен фильм {}", film.getName());
        idCounter++;
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        log.info("В коллекции обновлен фильм {}", film.getName());
        return film;
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }
}