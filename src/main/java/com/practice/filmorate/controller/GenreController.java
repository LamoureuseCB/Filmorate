package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
@Slf4j

public class GenreController {
    private final FilmService filmService;

    @GetMapping
    public Set<Genre> findAllGenres() {
        log.info("GET / genres");
        return filmService.findAllGenres();
    }

    @GetMapping("/{id}")
    public Genre findGenreById(@PathVariable("id") int id) {
        log.info("GET / genres / {}", id);
        return filmService.findGenreById(id);
    }
}
