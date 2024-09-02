package com.practice.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor

public class Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 200)
    private String name;
    private String description;
    //    анннотация для будущей даты???
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

}
