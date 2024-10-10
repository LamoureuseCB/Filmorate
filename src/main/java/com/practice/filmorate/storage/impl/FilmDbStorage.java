package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;

import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.MpaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final String select = """
             select films.id,
                    films.name,
                    films.release_date,
                    films.description,
                    films.duration,
                    mpa_ratings.id as mpaId,
                    mpa_ratings.name as mpaName,
                    mpa_ratings.description as mpaDesc
             from films
                 join mpa_ratings on films.rating_mpa_id = mpa_ratings.id
            """;
    private final MpaStorage mpaStorage;

    @Override
    public Film create(Film film) {
        if (mpaStorage.findMpaById(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("ошибка");
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("name", film.getName());
        map.put("description", film.getDescription());
        map.put("releaseDate", film.getReleaseDate());
        map.put("duration", film.getDuration());

        Number generatedId = insert.executeAndReturnKey(map);
        film.setId(generatedId.intValue());
        return film;
    }


    @Override
    public Film update(Film film) {
        if (mpaStorage.findMpaById(film.getMpa().getId()).isEmpty()) {
            throw new ValidationException("ошибка");
        }

        String updateQuery = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, rating_mpa_id = ? WHERE id = ?";
        int updated = jdbcTemplate.update(updateQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

        if (updated == 0) {
            throw new NotFoundException("Фильм с ID " + film.getId() + " не найден");
        }

        return findById(film.getId()).orElseThrow(() -> new NotFoundException("Фильм с ID " + film.getId() + " не найден после обновления"));
    }


    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(select, this::mapRaw);
    }

    @Override
    public Optional<Film> findById(int id) {
        String filmByIdQuery = select + " where films.id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(filmByIdQuery, this::mapRaw,id));
    }

    private Film mapRaw(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpaId");
        String mpaName = rs.getString("mpaName");
        String mpaDesc = rs.getString("mpaDesc");
        Mpa ratingMpa = new Mpa(mpaId, mpaName, mpaDesc);
        return new Film(id, name, description, releaseDate, duration, ratingMpa);
    }

}







