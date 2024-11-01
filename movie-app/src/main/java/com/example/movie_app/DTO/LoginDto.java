package com.example.movie_app.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "username can't be empty")
    private String userName;
    @NotBlank(message = "password can't be empty")
    private String password;
}
