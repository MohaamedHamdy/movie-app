package com.example.movie_app.exception;

public class GenreNotFound extends RuntimeException {
    public GenreNotFound(String name) {
        super("Genre " + name + " not found");
    }
}
