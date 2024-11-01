package com.example.movie_app.repository;

import com.example.movie_app.DTO.MovieSearchDto;
import com.example.movie_app.entity.Actor;
import com.example.movie_app.entity.Genre;
import com.example.movie_app.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    public Set<Movie> searchMovieByTitle(@Param("title") String title);
    //    public Set<Movie> searchMovieByTitle(String title);
    public Set<Movie> searchMovieByActors(Set<Actor> actors);
    @Query("SELECT m FROM Movie m WHERE LOWER(m.director) LIKE LOWER(CONCAT('%', :director, '%'))")
    public Set<Movie> searchMovieByDirector(@Param("director") String director);
    public Set<Movie> searchMovieByGenres(Genre genre);

    public Set<Movie> findMoviesByActors(Actor actor);

    Set<Movie> findMoviesByImdbRatingGreaterThanEqual(double rate);

    Set<Movie> findTop10ByOrderByReleaseDateDesc();



}
