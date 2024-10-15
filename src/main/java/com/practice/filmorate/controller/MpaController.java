package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.service.FilmService;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final FilmService filmService;

    @GetMapping
    public List<Mpa> findAllMpa() {
        log.info("GET / mpa");
        return filmService.findAllMpa();
    }

    @GetMapping("/{id}")
    public Mpa findMpaById(@PathVariable("id") int id) {
        log.info("GET / mpa / {}", id);
        return filmService.findMpaById(id);
    }


}
