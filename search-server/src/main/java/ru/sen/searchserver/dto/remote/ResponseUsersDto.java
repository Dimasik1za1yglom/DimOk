package ru.sen.searchserver.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sen.searchserver.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsersDto {

    /**
     * Contains a list of user if success is true, and null if success is false
     */
    List<User> users;

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
