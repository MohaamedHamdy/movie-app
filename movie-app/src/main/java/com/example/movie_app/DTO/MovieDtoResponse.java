package com.example.movie_app.DTO;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MovieDtoResponse{
    private long movieId;
    private String title;
    private String description;
    private int duration;
    private String director;
    private LocalDateTime releaseDate;
    private String posterUrl;
    private String trailerUrl;
    private Set<Genre> genreSet;
    private Set<Actor> actorSet;
    private double imdbRating;
    private String imdbRatingCount;

}
