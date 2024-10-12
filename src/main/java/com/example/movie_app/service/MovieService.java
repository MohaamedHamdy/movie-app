package com.example.movie_app.service;

import com.example.movie_app.DTO.MovieDto;
import com.example.movie_app.DTO.MovieDtoMapper;
import com.example.movie_app.DTO.MovieDtoResponse;
import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import com.example.movie_app.exception.ActorNotFound;
import com.example.movie_app.exception.GenreNotFound;
import com.example.movie_app.exception.MovieNotFound;
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

import java.util.*;

@Service
public class MovieService {

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;
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


    // ADD MOVIE
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

    // GET MOVIE DETAILS BY ID
    public ResponseEntity<SuccessResponse> getMovieDetails(long movieId) {
        logger.info("Getting movie details");
        Movie movie;
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        if (movieOptional.isPresent()) {
            movie = movieOptional.get();
        } else {
            throw new MovieNotFound();
        }
        logger.info("Movie Details" + movie);
        MovieDtoResponse movieDto = MovieDtoMapper.toMovieDtoResponse(movie);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(movieDto);
        successResponse.setMessage("Movie details retrieved successfully");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // GET ALL MOVIES
    public ResponseEntity<SuccessResponse> getAllMovies() {
        logger.info("Getting all movies");
        List<Movie> movies = movieRepository.findAll();
        List<MovieDtoResponse> movieDtoResponseList = new ArrayList<>();
        for (Movie movie : movies) {
            MovieDtoResponse movieDtoResponse = MovieDtoMapper.toMovieDtoResponse(movie);
            movieDtoResponseList.add(movieDtoResponse);
        }
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(movieDtoResponseList);
        successResponse.setMessage("All movies retrieved successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


    // SEARCH MOVIE BY GENRE, ACTORS, DIRECTOR, TITLE (NOT WORKING)
    public ResponseEntity<SuccessResponse> searchMovies(String search) {
        logger.info("Searching movies with keyword: {}", search);

        Set<Movie> movieSet = new HashSet<>();

        // Search by title
        movieSet.addAll(movieRepository.searchMovieByTitle(search));

        // Search by director if no result from title search
        if (movieSet.isEmpty()) {
            movieSet.addAll(movieRepository.searchMovieByDirector(search));
        }

        // Search by actor if still empty
        if (movieSet.isEmpty()) {
            Actor actor = actorRepository.findByName(search);
            if (actor != null) {
                Set<Actor> actors = new HashSet<>();
                actors.add(actor);
                movieSet.addAll(movieRepository.searchMovieByActors(actors));
            }
        }

        // Search by genre if still empty
        if (movieSet.isEmpty()) {
            Genre genre = genreRepository.findByName(search);
            if (genre != null) {
                Set<Genre> genreSet = new HashSet<>();
                genreSet.add(genre);
                movieSet.addAll(movieRepository.searchMovieByGenres(genreSet));
            }
        }

        // If no movies were found, throw custom exception
        if (movieSet.isEmpty()) {
            throw new MovieNotFound();
        }

        // Create the success response
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(movieSet);
        successResponse.setMessage("Search movies retrieved successfully");

        logger.info("Movies found: {}", movieSet.size());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
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
