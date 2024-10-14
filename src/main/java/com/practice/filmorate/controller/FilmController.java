package com.practice.filmorate.controller;
import com.practice.filmorate.model.Film;
import com.practice.filmorate.service.FilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {


    private final FilmService filmService;
    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("POST / film / {}", film.getName());
        return filmService.create(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT / film / {}", film.getName());
        return filmService.update(film);
    }

    @GetMapping
    public Collection<Film> getAllFilms() {
        log.info("GET / films");
        return filmService.getAllFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("GET / {}", id);
        return filmService.getFilmById(id);
    }


    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("PUT / {} / like / {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) {
        log.info("DELETE / {} / like / {}", id, userId);
        filmService.removeLike(id, userId);

    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("GET / popular");
        return filmService.getTenPopularFilms(count);
    }

}