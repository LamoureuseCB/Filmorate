package com.practice.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private int id;
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[^\\s]+$", message = "Логин не может содержать пробелы")
    private String login;
    @Getter
    @Setter
    private String name;
    @NotNull(message = "Дата рождения не может быть пустой")
    @PastOrPresent
    private LocalDate birthday;

}
