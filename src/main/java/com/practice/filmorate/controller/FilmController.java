package com.practice.filmorate.controller;

import com.practice.filmorate.model.Film;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();
    public static int idCounter = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        if(film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))){
            throw new ValidationException();
        }
        film.setId(idCounter);
        films.put(idCounter, film);
        log.info("В коллекцию добавлен фильм {}", film.getName());
        idCounter++;
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Нет фильма с данным ID");
        }
        films.put(film.getId(), film);
        log.info("В коллекции обновлен фильм {}", film.getName());
        return film;
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return films.values();
    }

}
