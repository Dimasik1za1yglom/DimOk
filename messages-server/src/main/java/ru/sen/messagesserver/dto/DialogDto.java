package ru.sen.messagesserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DialogDto {

    @NotBlank(message = "Название диалога не должно состоять из пробелов или быть пустым")
    @Size(min = 1, max = 100, message = "Название диалога должно содержать от 1 до 100 символов")
    String name;
}
