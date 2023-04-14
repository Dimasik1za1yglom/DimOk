package ru.sen.accountserver.services;

import ru.sen.accountserver.entity.User;

import java.util.List;

public interface UsersSearchService {


    List<User> getAllUsers();

    List<User> getUsersByLastName(String lastName);

    List<User> getUsersByFirstName(String firstName);

    List<User> getUsersByFirstNameAndLastName(String lastName, String firstName);

}
