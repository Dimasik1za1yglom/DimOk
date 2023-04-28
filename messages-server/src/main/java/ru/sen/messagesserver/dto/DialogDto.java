package ru.sen.messagesserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DialogDto {

    @NotBlank(message = "Поле Имя не должно быть пустым или состоять из пробелов")
    @Size(min = 2, max = 30, message = "Поле Имя должно содержать от 4 до 30 символов")
    private String firstName;

    @NotBlank(message = "Поле Фамилия не должно быть пустым или состоять из пробелов")
    @Size(min = 2, max = 30, message = "Поле Фамилия должно содержать от 4 до 30 символов")
    private String lastName;
}
