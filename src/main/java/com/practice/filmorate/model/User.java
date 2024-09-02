package com.practice.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
public class User {
    private int id;
    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    private String email;
    @NotBlank(message = "Логин не может быть пустым")
//    аннотация для пробелов???
    private String login;
    private String name;
    @NotNull
//    аннотация для будущего времени???
    private LocalDate dateOfBirth;

}
