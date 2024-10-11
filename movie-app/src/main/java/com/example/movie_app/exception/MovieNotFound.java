package com.example.movie_app.exception;

public class MovieNotFound extends RuntimeException {
    public MovieNotFound() {
        super("Movie Not Found");
    }
}
