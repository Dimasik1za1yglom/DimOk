package ru.sen.accountserver.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.accountserver.dto.ResponseUsersDto;

public interface RemoteApi {

    @GetMapping("/all")
    ResponseUsersDto getAllUsers();

    @GetMapping("/lastName")
    ResponseUsersDto getUsersByLastName(@RequestParam String lastName);

    @GetMapping("/firstName")
    ResponseUsersDto getUsersByFirstName(@RequestParam String firstName);

    @GetMapping("/fullName")
    ResponseUsersDto getUsersByFirstNameAndLastName(@RequestParam String lastName, @RequestParam String firstName);
}
