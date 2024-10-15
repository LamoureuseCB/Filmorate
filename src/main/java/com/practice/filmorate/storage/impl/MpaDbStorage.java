package com.practice.filmorate.storage.impl;


import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
import com.practice.filmorate.storage.mappers.MpaMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor

public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        String mpaQuery = "select * from mpa_ratings";
        return jdbcTemplate.query(mpaQuery, new MpaMapper());
    }

    @Override
    public Optional<Mpa> findMpaById(int id) {
        String ratingMpaByIdQuery = "select * from mpa_ratings where id = ?";
        return jdbcTemplate.query(ratingMpaByIdQuery, new MpaMapper(), id).stream().findFirst();
    }
}
