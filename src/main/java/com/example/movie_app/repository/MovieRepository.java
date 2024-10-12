package com.example.movie_app.repository;

import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    public Set<Movie> searchMovieByTitle(String title);
//    public Set<Movie> searchMovieByDirector(String director);
    public Set<Movie> searchMovieByActors(Set<Actor> actors);
    public Set<Movie> searchMovieByDirector(String director);
    public Set<Movie> searchMovieByGenres(Set<Genre> genres);

}
