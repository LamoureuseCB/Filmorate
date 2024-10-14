package com.practice.filmorate.storage.mappers;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Mpa;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FilmMapper implements RowMapper<Film> {
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
//        int likesCount = rs.getInt("likesCount");
        Mpa ratingMpa = new Mpa(mpaId, mpaName, mpaDesc);
        return new Film(id, name, description, releaseDate, duration, ratingMpa);
    }
}
