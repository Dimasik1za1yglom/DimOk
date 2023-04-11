package ru.sen.searchserver.dto;

import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class SearchRequestDto {

    @Max(value = 30, message = "Поле Имя должно содержать до 30 символов")
    private String firstName;

    @Max(value = 30, message = "Поле Фамилия должно содержать до 30 символов")
    private String lastName;
}
