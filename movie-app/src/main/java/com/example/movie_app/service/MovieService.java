package com.example.movie_app.service;

import com.example.movie_app.DTO.MovieDto;
import com.example.movie_app.response.SuccessResponse;
import org.springframework.http.ResponseEntity;

public interface MovieService {

    /*
        2. Movie Discovery
            Movie Listings: Display a catalog of movies with details (title, poster, release date, description, etc.).
            Movie Search: Provide a search feature to find movies by title, genre, director, or actors.
            Movie Filters: Allow users to filter by genre, year, or rating.
            Featured Movies: Highlight new releases or trending movies on the homepage.
            Recommended Movies: Suggest movies based on user watch history or preferences.
        3. Movie Details
            Movie Information: Show detailed information for each movie (synopsis, cast, crew, trailer, release year, etc.).
            Ratings and Reviews: Users can leave reviews and rate movies.
            Favorite/Watchlist: Users can add movies to their favorites or watchlist for easy access.
            Similar Movies: Suggest similar movies based on genre, director, or cast.
     */

    /*
        Spring Security



        1- well about the recommendation I will recommend based on the watchlist of the user so
        we will get the movies in the watch list get its actors, genres, director and recommend the same.

    */

    ResponseEntity<SuccessResponse> createMovie(MovieDto movieDto);
    ResponseEntity<SuccessResponse> getMovieDetails(long movieId);
    ResponseEntity<SuccessResponse> getAllMovies();
    ResponseEntity<SuccessResponse> searchMovies(String search);
    ResponseEntity<SuccessResponse> similarMovies(long movieId);
    ResponseEntity<SuccessResponse> topRatedMovies(); // featured
    ResponseEntity<SuccessResponse> findNewMovies(); // featured
    ResponseEntity<SuccessResponse> filterByGenre();
    ResponseEntity<SuccessResponse> filterByActor();
    ResponseEntity<SuccessResponse> filterByYear();
    ResponseEntity<SuccessResponse> filterByRate(double rate);



}
