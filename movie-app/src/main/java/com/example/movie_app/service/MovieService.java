package com.example.movie_app.service;

import com.example.movie_app.DTO.MovieDto;
import com.example.movie_app.DTO.MovieDtoMapper;
import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.exception.ActorNotFound;
import com.example.movie_app.exception.GenreNotFound;
import com.example.movie_app.repository.ActorRepository;
import com.example.movie_app.repository.GenreRepository;
import com.example.movie_app.repository.MovieRepository;
import com.example.movie_app.response.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    private MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.actorRepository = actorRepository;
    }

    public ResponseEntity<SuccessResponse> createMovie(MovieDto movieDto) {
        logger.info("Creating movie dto: {}", movieDto);
        SuccessResponse successResponse = new SuccessResponse();
        Set<Genre> genreList = new HashSet<>();
        Genre genre;
        for (String genreName : movieDto.getGenres()) {
            genre = genreRepository.findByName(genreName);
            if (genre != null) {
                genreList.add(genre);
            } else {
                throw new GenreNotFound(genreName);
            }
        }
        logger.info(genreList.toString());
        Set<Actor> actorList = getActorById(movieDto.getActors_id());
        logger.info(actorList.toString());
        Movie movie = MovieDtoMapper.toMovieEntity(movieDto);
        movie.setGenres(genreList);
        movie.setActors(actorList);
        logger.info(movie.toString());
        Movie savedMovie = movieRepository.save(movie);
        successResponse.setMessage("Movie created successfully");
        successResponse.setCode(HttpStatus.CREATED.value());
        successResponse.setData(MovieDtoMapper.toMovieDtoResponse(savedMovie));
//        return ResponseEntity.ok(MovieDtoMapper.toMovieDto(savedMovie));
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }


    public Set<Actor> getActorById(Set<Long> id) {
        Set<Actor> actors = new HashSet<>();
        Optional<Actor> actor;
        for (long actorId : id) {
            actor = actorRepository.findById(actorId);
            if (actor.isPresent()) {
                actors.add(actor.get());
            } else {
                throw new ActorNotFound(actorId);
            }
        }
        return actors;
    }


}
