package com.example.movie_app.controller;

import com.example.movie_app.DTO.MovieDto;
import com.example.movie_app.response.SuccessResponse;
import com.example.movie_app.service.MovieServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieServiceImpl movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/admin")
    public ResponseEntity<SuccessResponse> createMovie(@Valid @RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getMovieById(@Valid @PathVariable Long id) {
        return movieService.getMovieDetails(id);
    }
    @GetMapping("/getAll")
    public ResponseEntity<SuccessResponse> getAllMovie() {
        return movieService.getAllMovies();
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<SuccessResponse> searchMovie(@PathVariable String search) {
        return movieService.searchMovies(search);
    }

    @GetMapping("/similarMovies/{id}")
    public ResponseEntity<SuccessResponse> similarMovies(@PathVariable long id) {
        return movieService.similarMovies(id);
    }

    @GetMapping("/topRated")
    public ResponseEntity<SuccessResponse> topRatedMovies() {
        return movieService.topRatedMovies();
    }

    @GetMapping("/filterByGenre")
    public ResponseEntity<SuccessResponse> filterByGenre() {
        return movieService.filterByGenre();
    }

    @GetMapping("/filterByActor")
    public ResponseEntity<SuccessResponse> filterByActor() {
        return movieService.filterByActor();
    }

    @GetMapping("/filterByYear")
    public ResponseEntity<SuccessResponse> filterByYear() {
        return movieService.filterByYear();
    }


    @GetMapping("/filterByRate/{rate}")
    public ResponseEntity<SuccessResponse> filterByRate(@PathVariable double rate) {
        return movieService.filterByRate(rate);
    }

    @GetMapping("/newMovies")
    public ResponseEntity<SuccessResponse> findNewMovies() {
        return movieService.findNewMovies();
    }


}
