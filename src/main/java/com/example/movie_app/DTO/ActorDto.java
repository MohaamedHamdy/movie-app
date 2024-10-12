package com.example.movie_app.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActorDto {
    @NotBlank(message = "Actor's name cant be empty")
    private String name;
    @NotBlank(message = "Actor's image cant be empty")
    private String image;
}
