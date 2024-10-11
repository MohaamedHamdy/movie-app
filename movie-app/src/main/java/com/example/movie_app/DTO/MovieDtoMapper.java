package com.example.movie_app.DTO;


import com.example.movie_app.entity.Movie;

public class MovieDtoMapper {
    public static Movie toMovieEntity(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setDescription(movieDto.getDescription());
        movie.setTitle(movieDto.getTitle());
        movie.setDuration(movieDto.getDuration());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());
        movie.setTrailerUrl(movieDto.getTrailerUrl());
        movie.setDirector(movieDto.getDirector());
        movie.setGenres(movieDto.getGenreSet());
        movie.setActors(movieDto.getActorSet());
        return movie;
    }

    public static MovieDto toMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setDescription(movie.getDescription());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDuration(movie.getDuration());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setPosterUrl(movie.getPosterUrl());
        movieDto.setTrailerUrl(movie.getTrailerUrl());
        movieDto.setDirector(movie.getDirector());
        movieDto.setGenreSet(movie.getGenres());
        movieDto.setActorSet(movie.getActors());
        return movieDto;
    }
    public static MovieDtoResponse toMovieDtoResponse(Movie movie) {
        MovieDtoResponse movieDto = new MovieDtoResponse();
        movieDto.setMovieId(movie.getId());
        movieDto.setDescription(movie.getDescription());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDuration(movie.getDuration());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setPosterUrl(movie.getPosterUrl());
        movieDto.setTrailerUrl(movie.getTrailerUrl());
        movieDto.setDirector(movie.getDirector());
        movieDto.setGenreSet(movie.getGenres());
        movieDto.setActorSet(movie.getActors());
        return movieDto;
    }
}
