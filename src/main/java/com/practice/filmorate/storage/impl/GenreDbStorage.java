package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import com.practice.filmorate.storage.mappers.GenreMapper;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Component
@RequiredArgsConstructor

public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Set<Genre> findAll() {
        String genresQuery = "select * from genres";
        return new HashSet<>(jdbcTemplate.query(genresQuery, new GenreMapper()));

    }


    @Override
    public Optional<Genre> findGenreById ( int genreId){
        String genresQuery = "select * from genres where id = ?";

        return jdbcTemplate.queryForStream(genresQuery, new GenreMapper(), genreId).findFirst();
    }

}
