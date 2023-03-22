package ru.sen.accountserver.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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

    @Past
    private Date birthday;

    private String bio;
    private String country;
    private String city;

    @NotBlank
    @Size(min = 1, max = 80, message = "Поле phone не должно быть пустым")
    private String phone;
}
