package ru.sen.accountserver.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDialogDto {

    Long dialogId;
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
