package com.example.movie_app.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreDto {
    // must be unique and we will handle it in logic
    @NotBlank(message = "Genre's name can't be empty")
    private String name;

}
