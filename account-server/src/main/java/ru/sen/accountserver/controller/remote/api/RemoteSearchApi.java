package ru.sen.accountserver.controller.remote.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sen.accountserver.dto.remote.ResponseUsersDto;

public interface RemoteSearchApi {

    @GetMapping("/all")
    ResponseUsersDto getAllUsers();

    @GetMapping("/lastName")
    ResponseUsersDto getUsersByLastName(@RequestParam String lastName);

    @GetMapping("/firstName")
    ResponseUsersDto getUsersByFirstName(@RequestParam String firstName);

    @GetMapping("/fullName")
    ResponseUsersDto getUsersByFirstNameAndLastName(@RequestParam String lastName, @RequestParam String firstName);
}
