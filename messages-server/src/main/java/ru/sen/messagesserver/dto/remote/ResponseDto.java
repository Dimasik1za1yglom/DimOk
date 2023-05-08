package ru.sen.messagesserver.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    /**
     * contains true if there are no errors,
     * false if there are errors
     */
    boolean success;

    /**
     * contains an error message if there is one
     */
    String message;
}
