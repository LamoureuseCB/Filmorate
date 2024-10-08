package com.practice.filmorate.dao;

import com.practice.filmorate.model.Film;
import com.practice.filmorate.model.Genre;
import com.practice.filmorate.model.RatingMpa;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FilmDaoImpl implements FilmDao {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public Film create(Film film) {
        String createQuery = "insert into films (title,description,release_year,duration,rating_mpa_id ) " +
                "values (?,?,?,?,?)";
        jdbcTemplate.batchUpdate(createQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpaRating().getId);
        return film;
    }


    @Override
    public Film update(Film film) {
        String updateQuery = "update films set title = ?, description = ?, release_year = ?, duration = ?, mpa_rating = ? where id = ?";
        jdbcTemplate.batchUpdate(updateQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getRatingMpa().getId(), film.getId());
        return film;
    }

        public void deleteFromFilm (Film film){
            String deleteFilmQuery = "delete from films where id = ?";
            jdbcTemplate.batchUpdate(deleteFilmQuery, film.getId());
        }

        public void deleteFromFilmGenre (Film film){
            String deleteQuery = "delete from films_genres where films.id = ?";
            jdbcTemplate.batchUpdate(deleteQuery, film.getId());
//            конфликт id и string
        }

        @Override
        public Collection<Film> findAllFilms () {
            String allFilmsQuery = "SELECT * FROM films";
            return jdbcTemplate.query(allFilmsQuery, this::mapRaw);
        }
        @Override
        public List<Film> findFilmById (int id){
            String filmByIdQuery = "select * from films where id = ?";
            return jdbcTemplate.query(filmByIdQuery, this::mapRaw);
        }

        @Override
        public Set<Genre> findGenres () {
            String genresQuery = "select * from genres";
            return new HashSet<>(jdbcTemplate.query(genresQuery, this::genreMapRow));

        }


        @Override
        public Optional<Genre> findGenreById ( int genreId){
            return Optional.empty();
        }

        public Set<Genre> findGenreByFilmId ( int filmId){
            String genreByFilmIdQuery = """
                    select genres.*
                        from genres
                        join films_genres on genres.id = films_genres.genre_id
                        where films_genres.film_id = ?
                    """;
            return new HashSet<>(jdbcTemplate.query(genreByFilmIdQuery, this::genreMapRow)))
        }

        @Override
        public Set<RatingMpa> findRatingMpa () {
            String ratingMpaQuery = "select * from mpa_ratings";
            return new HashSet<>(jdbcTemplate.query(ratingMpaQuery, this::mpaMapRow));
        }

        @Override
        public Optional<RatingMpa> findRatingMpaById ( int ratingMpaId){
            String ratingMpaByIdQuery = "select * from mpa_ratings where id = ?";
            return jdbcTemplate.queryForObject(ratingMpaByIdQuery, this::mpaMapRow);
        }


        private Film mapRaw (ResultSet rs,int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            int releaseYear = rs.getDate("release_year").toLocalDate().getYear();
            int duration = rs.getInt("duration");
            RatingMpa ratingMpa = findRatingMpaById(rs.getInt("mpa_id")).orElse(null);
            return new Film(id, title, description, releaseYear, duration, ratingMpa);
        }

        private RatingMpa mpaMapRow (ResultSet rs,int rowNum) throws SQLException {
            int mpaId = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new RatingMpa(mpaId, name, description);
        }

        private Genre genreMapRow (ResultSet rs,int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            return new Genre(id, name);
        }
}







