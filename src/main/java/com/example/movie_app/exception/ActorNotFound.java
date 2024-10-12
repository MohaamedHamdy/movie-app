package com.example.movie_app.exception;

public class ActorNotFound extends RuntimeException {
    public ActorNotFound(long id) {
        super("Actor with id " + id + " not found");
    }
}
