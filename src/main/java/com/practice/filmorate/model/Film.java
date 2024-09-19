package com.practice.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым")

    private String name;
    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

}
