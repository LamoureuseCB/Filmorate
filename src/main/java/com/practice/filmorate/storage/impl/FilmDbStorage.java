package com.practice.filmorate.storage.impl;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.exceptions.ValidationException;
import com.practice.filmorate.model.Film;

import com.practice.filmorate.model.Genre;

import com.practice.filmorate.storage.FilmStorage;
import com.practice.filmorate.storage.MpaStorage;
import com.practice.filmorate.storage.mappers.FilmMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Component
@RequiredArgsConstructor
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
                    mpa_ratings.description as mpaDesc,
                    count(likes.film_id) as likeCount
             from films
                 join mpa_ratings on films.rating_mpa_id = mpa_ratings.id
            """;
    private final MpaStorage mpaStorage;


    @Override
    @Transactional
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
        map.put("release_date", film.getReleaseDate());
        map.put("duration", film.getDuration());
        map.put("rating_mpa_id", film.getMpa().getId());

        Number generatedId = insert.executeAndReturnKey(map);
        film.setId(generatedId.intValue());
        updateGenre(film);
        return film;
    }


    @Override
    public Film update(Film film) {
        if (!mpaStorage.findMpaById(film.getMpa().getId()).isEmpty()) {
            String updateQuery = "update films set name = ?, description = ?, release_date = ?, duration = ?, rating_mpa_id = ? where id = ?";
            int updated = jdbcTemplate.update(updateQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());

            if (updated == 0) {
                throw new NotFoundException("Фильм с ID " + film.getId() + " не найден");
            }
            updateGenre(film);
            return film;
        } else {
            throw new ValidationException("ошибка");
        }

    }

    @Override
    public Collection<Film> findAll() {
        return jdbcTemplate.query(select, new FilmMapper());
    }

    @Override
    public Optional<Film> findById(int id) {
        String filmByIdQuery = select + " where films.id = ?";
        return jdbcTemplate.queryForStream(filmByIdQuery, new FilmMapper(), id).findFirst();
    }

    @Override
    public List<Film> getTenPopularFilms(int count) {
        String sql = "left join likes on films.id = likes.film_id" +
                "group by films.id, mpa_ratings.id" +
                "order by like_count desc" +
                "limit ?";
        return jdbcTemplate.query(select + sql, new FilmMapper(), count);
    }

    private void updateGenre(Film film) {
        String sql = "insert into films_genres (film_id, genre_id) values(?, ?)";
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update(sql, film.getId(), genre.getId());
        }
    }
}








