package ru.sen.accountserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authorization_data")
public class AuthorizationData {

    @Id
    @NotNull
    private String email;

    @NotNull
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}

