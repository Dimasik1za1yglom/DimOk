package ru.sen.messagesserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MessageDto {

    @NotBlank(message = "Сообщение не должно состоять из пробелов или быть пустым")
    @Size(min = 1, max = 50000, message = "Сообщение должно содержать от 1 до 50000 символов")
    private String textMessage;

    @NotNull
    private Long dialogId;
}
