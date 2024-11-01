package com.example.movie_app.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieSearchDto {
    private long id;
    private String title;
    private String posterUrl;
    private double imdbRating;
    private String imdbRatingCount;
    private LocalDateTime releaseDate;
}
