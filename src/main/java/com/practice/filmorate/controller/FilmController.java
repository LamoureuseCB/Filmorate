package com.practice.filmorate.controller;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;


    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().update(film);
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        return filmService.getFilmStorage().getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmStorage().getFilmById(id);
    }

}
