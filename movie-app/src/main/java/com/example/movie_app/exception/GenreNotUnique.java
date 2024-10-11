package com.example.movie_app.exception;

public class GenreNotUnique extends RuntimeException {
    public GenreNotUnique() {
        super("Genre must be unique." );
    }
}
