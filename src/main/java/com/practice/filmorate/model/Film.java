package com.practice.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым")
    @Size(max = 200)
    private String name;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
    @Past(message = "Дата релиза не должна быть  в будущем ")
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Название не может быть пустым") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Название не может быть пустым") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Описание не может быть пустым") @Size(max = 200) String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Описание не может быть пустым") String description) {
        this.description = description;
    }

    public @Past(message = "Дата релиза не должна быть  в будущем ") LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@Past(message = "Дата релиза не должна быть  в будущем ") LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Positive(message = "Продолжительность фильма должна быть положительной")
    public int getDuration() {
        return duration;
    }

    public void setDuration(@Positive(message = "Продолжительность фильма должна быть положительной") int duration) {
        this.duration = duration;
    }
}
