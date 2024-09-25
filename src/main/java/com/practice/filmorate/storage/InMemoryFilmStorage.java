package com.practice.filmorate.storage;

import com.practice.filmorate.exceptions.NotFoundException;
import com.practice.filmorate.model.Film;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    Map<Integer, Film> films = new HashMap<>();
    public static int idCounter = 1;
    private static final String FILM_ID_EXC = "Фильм по ID не найден";


    @Override
    public Film createFilm(Film film) {
        if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException();
        }
        film.setId(idCounter);
        films.put(idCounter, film);
        log.info("В коллекцию добавлен фильм {}", film.getName());
        idCounter++;
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Нет фильма с данным ID");
        }
        films.put(film.getId(), film);
        log.info("В коллекции обновлен фильм {}", film.getName());
        return film;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values();
    }

    @Override
    public Film getFilmById(int id) {
        if(films.containsKey(id)) {
            return films.get(id);
        }
        else{
            throw new NotFoundException(FILM_ID_EXC);
        }
    }
}