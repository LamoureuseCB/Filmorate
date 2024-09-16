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
    @Setter
    private String name;
    @NotNull(message = "Дата рождения не может быть пустой")
    @Past
    private LocalDate dateOfBirth;


    public @NotBlank(message = "Логин не может быть пустым") @Pattern(regexp = "^[^\\s]+$", message = "Логин не может содержать пробелы") String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank(message = "Логин не может быть пустым") @Pattern(regexp = "^[^\\s]+$", message = "Логин не может содержать пробелы") String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public @NotBlank(message = "Электронная почта не может быть пустой") @Email(message = "Электронная почта должна содержать символ @") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Электронная почта не может быть пустой") @Email(message = "Электронная почта должна содержать символ @") String email) {
        this.email = email;
    }

    public @NotNull(message = "Дата рождения не может быть пустой") @PastOrPresent LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotNull(message = "Дата рождения не может быть пустой") @PastOrPresent LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
