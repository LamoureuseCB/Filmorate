package com.practice.filmorate.storage.impl;


import com.practice.filmorate.model.Mpa;
import com.practice.filmorate.storage.MpaStorage;
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
@Qualifier("mpaDbStorage")
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Set<Mpa> findMpa() {
        String mpaQuery = "select * from mpa_ratings";
        return new HashSet<>(jdbcTemplate.query(mpaQuery, this::mpaMapRow));
    }

    @Override
    public Optional<Mpa> findMpaById(int id) {
        String ratingMpaByIdQuery = "select * from mpa_ratings where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(ratingMpaByIdQuery, this::mpaMapRow, id));
    }

    private Mpa mpaMapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return new Mpa(id, name, description);
    }
}
