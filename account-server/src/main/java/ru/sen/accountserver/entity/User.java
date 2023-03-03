package ru.sen.accountserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private String firstName;
    private String lastName;
    private Date birthday;
    private String bio;
    private String country;
    private String city;
    private String phone;
    private Long roleId;
}
