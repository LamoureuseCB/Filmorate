package com.practice.filmorate.storage.mappers;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.GenreStorage;
import com.practice.filmorate.storage.impl.GenreDbStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class FilmMapper implements RowMapper<Film> {
    private final GenreStorage genreStorage;
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpaId");
        String mpaName = rs.getString("mpaName");
        String mpaDesc = rs.getString("mpaDesc");
        List<Genre> filmsGenres = genreStorage.findAllByFilmId(id);
        Mpa ratingMpa = new Mpa(mpaId, mpaName, mpaDesc);
        Film film = new Film(id, name, description, releaseDate, duration, ratingMpa);
        film.getGenres().addAll(filmsGenres);
        return film;
    }
}
