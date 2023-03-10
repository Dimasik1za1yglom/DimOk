package ru.sen.accountserver.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class UserForm {

    @NotBlank
    @Size(min = 1, max = 80, message = "Поле firstName не должно быть пустым")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 80, message = "Поле lastName не должно быть пустым")
    private String lastName;

    @NotBlank
    @Size(min = 1, max = 80, message = "Поле birthday не должно быть пустым")
    private Date birthday;

    private String bio;
    private String country;
    private String city;
    private String phone;
}
