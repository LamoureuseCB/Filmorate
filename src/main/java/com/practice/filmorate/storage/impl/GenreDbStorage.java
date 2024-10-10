package com.practice.filmorate.storage.impl;

import com.practice.filmorate.model.Genre;
import com.practice.filmorate.storage.GenreStorage;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Component
@RequiredArgsConstructor
@Qualifier("genreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private final String select = """
             select * from (select genres.id as id,
                    genres.name as name
             from genres join films_genres  on genres.id = films_genres.genre_id)  where films_genres.film_id = ?
            """;

    @Override
    public Set<Genre> findAll() {
        String genresQuery = "select * from genres";
        return new HashSet<>(jdbcTemplate.query(genresQuery, this::genreMapRow));

    }
    @Override
    public Optional<Genre> findGenreById ( int genreId){
        String genreByFilmIdQuery = select;
        return Optional.ofNullable((Genre) jdbcTemplate.query(genreByFilmIdQuery, this::genreMapRow, genreId));
    }
   private Genre genreMapRow (ResultSet rs, int rowNum) throws SQLException {
        int genreId = rs.getInt("id");
        String genreName = rs.getString("name");
        return new Genre(genreId, genreName);
    }

}
