package com.practice.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RatingMpa {
    private int id;
    private String name;
    private String description;
}
