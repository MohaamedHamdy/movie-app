package com.example.movie_app.service;

import com.example.movie_app.DTO.*;
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
public class MovieServiceImpl implements MovieService {
    Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository,
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
        logger.info("SAVED MOVIE " + savedMovie.toString());
        successResponse.setMessage("Movie created successfully");
        successResponse.setCode(HttpStatus.CREATED.value());
        successResponse.setData(MovieDtoMapper.toMovieDtoResponse(savedMovie));
        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);
    }

    // GET MOVIE DETAILS BY ID
    public ResponseEntity<SuccessResponse> getMovieDetails(long movieId) {
        logger.info("Getting movie details");
        Movie movie;
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if (movieOptional.isPresent()) {
            logger.info("Movie found " + movieOptional.get());
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


    // SEARCH MOVIE BY GENRE, ACTORS, DIRECTOR, TITLE (its more like find by not search TBH)
    public ResponseEntity<SuccessResponse> searchMovies(String search) {
        // I think I need to ignore lower or upper case and it be LIKE not WHERE kaza = kaza
        logger.info("Searching movies with keyword: {}", search);
//        List<Movie> movieSet = new ArrayList<>();

        // Search by title
        Set<Movie> movieSet = new HashSet<>(movieRepository.searchMovieByTitle(search));

        // Search by director if no result from title search
        if (movieSet.isEmpty()) {
            movieSet.addAll(movieRepository.searchMovieByDirector(search));
        }

        // Search by actor if still empty
        if (movieSet.isEmpty()) {
            String actorName = search.substring(0, 1).toUpperCase() + search.substring(1);
            Actor actor = actorRepository.searchByName(actorName);
            if (actor != null) {
                Set<Actor> actors = new HashSet<>();
                actors.add(actor);
                movieSet.addAll(movieRepository.searchMovieByActors(actors));
            }
        }

        // Search by genre if still empty
        if (movieSet.isEmpty()) {
            String genreName = search.substring(0, 1).toUpperCase() + search.substring(1);

            Genre genre = genreRepository.searchByName(genreName);
            if (genre != null) {
//                Set<Genre> genreSet = new HashSet<>();
//                genreSet.add(genre);
                movieSet.addAll(movieRepository.searchMovieByGenres(genre));
            }
        }
        // If no movies were found, throw custom exception
        if (movieSet.isEmpty()) {
            throw new MovieNotFound();
        }
        Set<MovieSearchDto> movieSearchDtoSet = new HashSet<>();
        for (Movie m : movieSet) {
            MovieSearchDto movieSearchDto = MovieDtoMapper.toMovieSearchDto(m);
            movieSearchDtoSet.add(movieSearchDto);
        }
        // Create the success response
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(movieSearchDtoSet);
        successResponse.setMessage(movieSearchDtoSet.size() + " movies retrieved successfully");

        logger.info("Movies found: {}", movieSet.size());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> similarMovies(long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        Movie movie = new Movie();
        if (movieOptional.isPresent()) {
            movie = movieOptional.get();
        }
        Set<Genre> genreSet = new HashSet<>(movie.getGenres());
//        Set<Actor> actorSet = new HashSet<>(movie.getActors());
        Set<Movie> movieSet = new HashSet<>();
        logger.info("movie" + genreSet);
        for (Genre genre : genreSet) {            movieSet.addAll(movieRepository.searchMovieByGenres(genre));
        }
//        movieSet.addAll(movieRepository.searchMovieByActors(actorSet));


//        movieSet.addAll(movieRepository.searchMovieByDirector(movie.getDirector()));
        // similar by director, actors, genres

        SuccessResponse successResponse = new SuccessResponse();
        Set<MovieSearchDto> movieSearchDtoSet = new HashSet<>();
        for (Movie m : movieSet) {
            movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
        }
        successResponse.setData(movieSearchDtoSet);
//        successResponse.setData(movieSet);
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setMessage(movieSet.size() + " movies retrieved successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> topRatedMovies() {
        List<Movie> movies = new ArrayList<>();
        movies = movieRepository.findAll();
        ;
        movies.sort(Comparator.comparingDouble(Movie::getImdbRating).reversed());
        SuccessResponse successResponse = new SuccessResponse();
//        successResponse.setData(movies);

        List<MovieSearchDto> movieSearchDtoSet = new ArrayList<>();
        for (Movie m : movies) {
            movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
        }
        successResponse.setData(movieSearchDtoSet);

//        successResponse.setData(movieSet);
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setMessage(movies.size() + " movies retrieved successfully");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> findNewMovies() {
        Set<Movie> movieSet = new HashSet<>(movieRepository.findTop10ByOrderByReleaseDateDesc());
        SuccessResponse successResponse = new SuccessResponse();

        List<MovieSearchDto> movieSearchDtoSet = new ArrayList<>();
        for (Movie m : movieSet) {
            movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
        }
        successResponse.setData(movieSearchDtoSet);

        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setMessage(movieSet.size() + " movies retrieved successfully");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> filterByGenre() {
        List<Genre> genreList = genreRepository.findAll();
        logger.info("Genre list : " + genreList);
        SuccessResponse successResponse = new SuccessResponse();
        Set<Movie> movieSet;
        Map<String, Set<MovieSearchDto>> moviesGenreMap = new HashMap<>();
        Set<MovieSearchDto> movieSearchDtoSet = new HashSet<>();
        for (Genre genre : genreList) {
            movieSet = movieRepository.searchMovieByGenres(genre);
            for (Movie m : movieSet) {
                movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
            }
            moviesGenreMap.put(genre.getName(), movieSearchDtoSet);
        }
        successResponse.setData(moviesGenreMap);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> filterByActor() {
        List<Actor> actorList = actorRepository.findAll();
        logger.info("Actor list : " + actorList);
        SuccessResponse successResponse = new SuccessResponse();
        Set<Movie> movieSet;
        Map<String, Set<MovieSearchDto>> moviesGenreMap = new HashMap<>();
        Set<MovieSearchDto> movieSearchDtoSet = new HashSet<>();
        for (Actor actor : actorList) {
            movieSet = movieRepository.findMoviesByActors(actor);
            for (Movie m : movieSet) {
                movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
            }
            moviesGenreMap.put(actor.getName(), movieSearchDtoSet);
        }
        successResponse.setData(moviesGenreMap);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> filterByYear() {
        List<Movie> movies;
        movies = movieRepository.findAll();

        movies.sort(Comparator.comparing(Movie::getReleaseDate));
        SuccessResponse successResponse = new SuccessResponse();
        List<MovieSearchDto> movieSearchDtoSet = new ArrayList<>();
        for (Movie m : movies) {
            movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
        }
        successResponse.setData(movieSearchDtoSet);

//        successResponse.setData(movieSet);
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setMessage(movies.size() + " movies retrieved successfully");

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SuccessResponse> filterByRate(double rate) {
        Set<Movie> movies = movieRepository.findMoviesByImdbRatingGreaterThanEqual(rate);
        List<MovieSearchDto> movieSearchDtoSet = new ArrayList<>();
        for (Movie m : movies) {
            movieSearchDtoSet.add(MovieDtoMapper.toMovieSearchDto(m));
        }
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setData(movieSearchDtoSet);
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setMessage(movies.size() + " movies retrieved successfully");

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
