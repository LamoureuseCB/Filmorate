package com.practice.filmorate.controller;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaStorage mpaStorage;

    @GetMapping
    public Set<Mpa> getAllMpa(){
        return mpaStorage.findMpa();
    }
    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id){
        return mpaStorage.findMpaById(id).orElseThrow(() -> new NotFoundException("Рейтинг MPA по ID не найден"));
    }


}
