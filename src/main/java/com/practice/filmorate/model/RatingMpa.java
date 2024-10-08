package com.practice.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class RatingMpa {
    private int id;
    private String name;
    private String description;
}
