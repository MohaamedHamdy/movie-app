package com.example.movie_app.service;

import com.example.movie_app.DTO.GenreDto;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.exception.GenreNotUnique;
import com.example.movie_app.repository.GenreRepository;
import com.example.movie_app.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public ResponseEntity<SuccessResponse> createGenre(GenreDto genreDto) {
        Genre genre = new Genre();
        genre.setName(genreDto.getName());
        if (genreRepository.existsByName(genre.getName())) {
            throw new GenreNotUnique();
        }
        genreRepository.save(genre);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Genre created successfully");
        successResponse.setData(genre);
        successResponse.setCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<SuccessResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("Success");
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(genres);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


}
