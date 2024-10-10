package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor

public class GenreController {
    private final GenreStorage genreStorage;

    @GetMapping
    public Set<Genre> getAllGenres(){
        return genreStorage.findAll();
    }
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id){
        return genreStorage.findGenreById(id).orElseThrow(() -> new NotFoundException("Жанр по ID не найден"));

    }
}
