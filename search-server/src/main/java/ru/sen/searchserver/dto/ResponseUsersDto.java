package ru.sen.searchserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sen.searchserver.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUsersDto {

    List<User> user;

    boolean success;

    String message;
}
