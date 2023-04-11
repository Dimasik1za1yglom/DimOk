package ru.sen.searchserver.dto;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class SearchRequestDto {

    @Max(value = 30, message = "Поле Имя не должно содержать больше 30 символов")
    private String firstName;

    @Max(value = 30, message = "Поле Фамилия не должно содержать больше 30 символов")
    private String lastName;
}
