package com.example.movie_app.DTO;

import com.example.movie_app.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.*;

@Data
public class RegisterDto {

    @NotBlank(message = "username can't be empty")
    private String userName;
    @NotBlank(message = "email can't be empty")
    private String email;
    @NotBlank(message = "password can't be empty")
    private String password;
    @NotBlank(message = "role can't be empty")
    private List<String> role;
}
