package ru.sen.accountserver.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {

    private String userName;

    /**
     * contains true if there are no errors,
     * false if there are errors
     */
    private boolean success;

    /**
     * contains an error message if there is one
     */
    private String message;
}
