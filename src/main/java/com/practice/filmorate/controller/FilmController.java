package com.practice.filmorate.controller;

import com.practice.filmorate.model.Film;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    List<Film> films = new ArrayList<>();


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        films.add(film);
        log.info("В коллекцию добавлен фильм {}", film.getName());
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("В коллекции обновлен фильм {}", film.getName());
        films.remove(film.getName());
        films.add(film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return films;
    }


}
