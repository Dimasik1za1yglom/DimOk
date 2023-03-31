package ru.sen.accountserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorizationDataDto {

    @Email
    @Size(min = 8, max = 100, message = "Неверно введено поле email.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 100, message = "Неверно введено поле password.")
    private String password;

}
