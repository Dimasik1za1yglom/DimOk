package ru.sen.accountserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorizationDataDto {

    @Email(message = "Неверный формат Email-а")
    @Size(min = 8, max = 100, message = "Поле Email должно содержать от 8 до 100 символов.")
    private String email;

    @NotBlank(message = "Поле password не должно быть пустым или состоять из пробелов")
    @Size(min = 4, max = 35, message = "Поле Password должно содержать от 8 до 35 символов.")
    private String password;

}
