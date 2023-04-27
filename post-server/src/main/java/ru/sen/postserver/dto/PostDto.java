package ru.sen.postserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

    @NotBlank(message = "Содержание поста не должно быть пустым или состоять из пробелов")
    @Size(min = 4, max = 50000, message = "Содержание поста должно содержать от 4 до 50000 символов")
    private String text;
}
