package ru.sen.accountserver.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {

    @NotBlank()
    @Size(min = 1, max = 80, message = "Поле firstName должно содержать от 1 до 80 символов")
    private String firstName;

    @NotBlank()
    @Size(min = 1, max = 80, message = "Поле lastName должно содержать от 1 до 80 символов")
    private String lastName;

    @PastOrPresent(message = "Дата должна быть прошедшего временного периода")
    private LocalDate birthday;

    private String bio;
    private String country;
    private String city;

    @NotBlank
    @Size(min = 1, max = 80, message = "Поле phone не должно быть пустым")
    private String phone;
}
