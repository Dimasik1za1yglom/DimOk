package ru.sen.accountserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.sen.accountserver.annotation.Phone;

import java.time.LocalDate;

@Data
public class UserDto {

    @NotBlank(message = "Поле Имя не должно быть пустым или состоять из пробелов")
    @Size(min = 2, max = 30, message = "Поле Имя должно содержать от 2 до 30 символов")
    private String firstName;

    @NotBlank(message = "Поле Фамилия не должно быть пустым или состоять из пробелов")
    @Size(min = 2, max = 30, message = "Поле Фамилия должно содержать от 2 до 30 символов")
    private String lastName;

    @NotNull(message = "Поле Дата рождения не должно быть пустым")
    @PastOrPresent(message = "Дата должна быть прошедшего временного периода")
    private LocalDate birthday;

    private String bio;
    private String country;
    private String city;

    @NotBlank(message = "Поле Телефон не должно быть пустым или состоять из пробелов")
    @Phone
    private String phone;
}
