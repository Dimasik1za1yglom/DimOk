package ru.sen.searchserver.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.sen.searchserver.annotation.AllFieldsNotBlank;

@Data
@AllFieldsNotBlank
public class SearchRequestDto {

    @Size(max = 30, message = "Поле Имя не должно содержать больше 30 символов")
    private String firstName;

    @Size(max = 30, message = "Поле Фамилия не должно содержать больше 30 символов")
    private String lastName;
}
