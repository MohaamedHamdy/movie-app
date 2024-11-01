package com.example.movie_app.controller;

import com.example.movie_app.DTO.GenreDto;
import com.example.movie_app.response.SuccessResponse;
import com.example.movie_app.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/admin")
    public ResponseEntity<SuccessResponse> createGenre(@Valid @RequestBody GenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<SuccessResponse> getAllGenres() {
        return genreService.getAllGenres();
    }
}
