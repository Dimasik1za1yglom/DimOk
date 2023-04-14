package ru.sen.accountserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sen.accountserver.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsersDto {

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
