package com.practice.filmorate.storage;

import com.practice.filmorate.model.Mpa;

import java.util.Optional;
import java.util.Set;

public interface MpaStorage {
    Set<Mpa> findMpa ();
    Optional<Mpa> findMpaById (int mpaId);
}
