package ru.sen.accountserver.dto.remote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDto {

    private String firstName;

    private String lastName;
}
