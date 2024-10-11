package com.example.movie_app.controller;

import com.example.movie_app.DTO.MovieDto;
import com.example.movie_app.response.SuccessResponse;
import com.example.movie_app.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMovie(@Valid @RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getMovieById(@Valid @PathVariable Long id) {
        return movieService.getMovieDetails(id);
    }

}
