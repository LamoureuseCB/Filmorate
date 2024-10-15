package com.practice.filmorate.storage;

import com.practice.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MpaStorage {
    List<Mpa> getAllMpa ();
    Optional<Mpa> findMpaById (int mpaId);
}
