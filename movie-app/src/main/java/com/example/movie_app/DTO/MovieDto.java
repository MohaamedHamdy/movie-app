package com.example.movie_app.DTO;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MovieDto {
    @NotBlank(message = "Title can't be empty")
    private String title;
    @NotBlank(message = "Description can't be empty")
    private String description;
    @NotNull
    @Min(value = 1, message = "Duration must be more than one minute")
    private int duration;
    @NotBlank(message = "Director can't be null")
    private String director;
    @PastOrPresent(message = "Release date must be in the past or present")
    private LocalDateTime releaseDate;
    @NotBlank(message = "Poster Url can't be null")
    private String posterUrl;
    @NotBlank(message = "Trailer Url can't be null")
    private String trailerUrl;

    @NotNull
    private double imdbRating;
    @NotBlank(message = "Count can't be null")
    private String imdbRatingCount;

    private Set<Long> actors_id;
    private Set<String> genres;
    private Set<Genre> genreSet;
    private Set<Actor> actorSet;
}

